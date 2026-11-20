package com.nutrimate.app.ui.home

import androidx.lifecycle.ViewModel
import com.nutrimate.app.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadFeaturedRecipes()
    }

    private fun loadFeaturedRecipes() {
        _uiState.update { currentState ->
            currentState.copy(
                featuredRecipes = RecipeRepository.recipes.take(3),
                isLoading = false
            )
        }
    }
}
