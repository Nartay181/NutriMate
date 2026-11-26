package com.nutrimate.app.ui.screens.recipes

import android.os.Message
import com.nutrimate.app.data.remote.dto.MealDto

sealed interface RecipesUiState {
    object Loading : RecipesUiState
    data class Success(
        val meals: List<MealDto>
    ) : RecipesUiState
    data class Error(
        val message: String
    ) : RecipesUiState
    object Empty : RecipesUiState
}