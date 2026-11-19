package com.nutrimate.app.ui.home

import com.nutrimate.app.data.model.Recipe

data class HomeUiState(
    val welcomeMessage: String = "Welcome to NutriMate",
    val featuredRecipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)
