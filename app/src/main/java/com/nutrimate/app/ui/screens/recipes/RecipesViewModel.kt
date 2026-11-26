package com.nutrimate.app.ui.screens.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimate.app.data.repository.MealRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import kotlinx.coroutines.CancellationException

class RecipesViewModel : ViewModel() {
    private var searchJob: Job? = null
    private val mealRepository = MealRepository()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory: StateFlow<String> = _selectedCategory.asStateFlow()

    private val _categories = MutableStateFlow(listOf("All"))
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    private val _uiState = MutableStateFlow<RecipesUiState>(RecipesUiState.Loading)
    val uiState: StateFlow<RecipesUiState> = _uiState.asStateFlow()

    fun loadCategories() {
        viewModelScope.launch {
            try {
                val result = mealRepository.getCategories()
                _categories.value = listOf("All") +
                        result.map { it.name }.distinct()
            } catch (e: IOException) {
                _categories.value = listOf("All")
            } catch (e: HttpException) {
                _categories.value = listOf("All")
            }
        }
    }

    fun loadRecipes() {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            _uiState.value = RecipesUiState.Loading

            try {
                val query = _searchQuery.value
                val category = _selectedCategory.value

                val meals = when {
                    query.isNotBlank() -> {
                        val results = mealRepository.searchMeals(query)
                        if (category == "All") {
                            results
                        } else {
                            results.filter {
                                it.category == category
                            }
                        }
                    }

                    category != "All" -> {
                        mealRepository.filterByCategory(category)
                    }

                    else -> {
                        mealRepository.searchMeals("")
                    }
                }

                _uiState.value = if (meals.isEmpty()) {
                    RecipesUiState.Empty
                } else {
                    RecipesUiState.Success(meals)
                }

            } catch (e: IOException) {
                _uiState.value = RecipesUiState.Error(
                    "Please check your internet connection."
                )
            } catch (e: HttpException) {
                _uiState.value = RecipesUiState.Error(
                    "Server error: ${e.code()}"
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _uiState.value = RecipesUiState.Error(
                    "Something went wrong. Please try again."
                )
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        loadRecipes()
    }

    fun onCategoryChange(category: String) {
        _selectedCategory.value = category
        loadRecipes()
    }

    init {
        loadCategories()
        loadRecipes()
    }
}