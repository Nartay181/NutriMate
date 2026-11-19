package com.nutrimate.app.navigation

import android.net.Uri

object AppRoutes {
    const val HOME = "home"
    const val RECIPES = "recipes"
    const val FAVORITES = "favorites"
    const val RECIPE_DETAILS = "recipe_details/{recipeId}"

    fun recipeDetails(recipeId: String): String =
        "recipe_details/${Uri.encode(recipeId)}"
}
