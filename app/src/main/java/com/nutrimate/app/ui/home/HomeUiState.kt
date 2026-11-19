package com.nutrimate.app.ui.home

data class HomeUiState(
    val welcomeMessage: String = "Welcome to NutriMate",
    val featuredRecipeIds: List<Int> = emptyList(),
    val isLoading: Boolean = false
)
