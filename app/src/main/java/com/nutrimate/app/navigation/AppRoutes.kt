package com.nutrimate.app.navigation

object AppRoutes {
    const val HOME = "home"
    const val RECIPES = "recipes"
    const val FAVORITES = "favorites"
    const val RECIPE_DETAILS = "recipe_details/{recipeId}"

    fun recipeDetails(recipeId: Int): String =
        "recipe_details/$recipeId"
}
