package com.nutrimate.app.navigation

object AppRoutes {
    const val HOME = "home"
    const val RECIPES = "recipes"
    const val RECIPES_WITH_CATEGORY = "recipes?category={category}"
    const val FAVORITES = "favorites"
    const val PROFILE = "profile"
    const val RECIPE_DETAILS = "recipe_details/{recipeId}"

    fun recipeDetails(recipeId: Int): String =
        "recipe_details/$recipeId"

    fun recipes(category: String): String = "recipes?category=${android.net.Uri.encode(category)}"
}
