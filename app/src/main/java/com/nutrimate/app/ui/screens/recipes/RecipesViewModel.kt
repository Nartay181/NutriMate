package com.nutrimate.app.ui.screens.recipes

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.data.repository.RecipeRepository
import kotlinx.coroutines.flow.combine
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn

class RecipesViewModel : ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory

    val filteredRecipes = _searchQuery.combine(_selectedCategory) {query, category ->
        RecipeRepository.recipes.filter { recipe ->
            val matchesSearch = recipe.name.contains(
                query,
                ignoreCase = true
            )
            val matchesCategory = category == "All" || recipe.category == category
            matchesSearch && matchesCategory
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = RecipeRepository.recipes
    )

    val categories = listOf("All") +
            RecipeRepository.recipes
                .map { recipe -> recipe.category}
                .distinct()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onCategoryChange(category: String) {
        _selectedCategory.value = category
    }
}