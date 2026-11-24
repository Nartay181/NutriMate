package com.nutrimate.app.ui.screens.home

import com.nutrimate.app.data.model.Recipe

data class HomeUiState(
    val welcomeMessage: String = "Good morning, Alex",
    val featuredRecipes: List<Recipe> = emptyList(),
    val isLoading: Boolean = false
)
