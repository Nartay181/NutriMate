package com.nutrimate.app.ui.screens.details

import androidx.lifecycle.ViewModel
import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
data class RecipeDetailsUiState(
    val recipe: Recipe? = null,
    val isLoading: Boolean = true
)
class RecipeDetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RecipeDetailsUiState())
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    fun loadRecipe(recipeId: Int) {
        val recipe = RecipeRepository.getRecipeById(recipeId)
        _uiState.update { currentState ->
            currentState.copy(
                recipe = recipe,
                isLoading = false
            ) }
    }
}