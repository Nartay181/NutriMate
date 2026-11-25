package com.nutrimate.app.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimate.app.data.repository.MealRepository
import com.nutrimate.app.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException

class RecipesViewModel : ViewModel() {
    private val mealRepository = MealRepository()

    private val _uiState = MutableStateFlow<RecipesUiState>(RecipesUiState.Loading)
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    init {
        loadRecipes()
    }

    fun loadRecipes() {
        viewModelScope.launch {
            _uiState.value = RecipesUiState.Loading

            try {
                val meals = mealRepository.searchMeals("")
                _uiState.value = if (meals.isEmpty()) {
                    RecipesUiState.Empty
                } else {
                    RecipesUiState.Success(meals)
                }
            } catch (e: IOException) {
                _uiState.value = RecipesUiState.Error(
                    "Please check your internet connection."
                )
            } catch (e: retrofit2.HttpException) {
                _uiState.value = RecipesUiState.Error(
                    "Server error: ${e.code()}"
                )
            }
        }
    }
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