package com.nutrimate.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nutrimate.app.ui.favorites.FavoritesScreen
import com.nutrimate.app.ui.home.HomeScreen
import com.nutrimate.app.ui.recipe_details.RecipeDetailsScreen
import com.nutrimate.app.ui.screens.recipes.RecipesScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME
    ) {
        composable(AppRoutes.HOME) {
            HomeScreen(
                onOpenRecipes = { navController.navigate(AppRoutes.RECIPES) },
                onOpenFavorites = { navController.navigate(AppRoutes.FAVORITES) }
            )
        }
        composable(AppRoutes.RECIPES) {
            RecipesScreen(
                onRecipeClick = { recipeId ->
                    navController.navigate(AppRoutes.recipeDetails(recipeId))
                }
            )
        }
        composable(AppRoutes.FAVORITES) {
            FavoritesScreen(onBack = { navController.popBackStack() })
        }
        composable(
            route = AppRoutes.RECIPE_DETAILS,
            arguments = listOf(navArgument("recipeId") { type = NavType.IntType })
        ) { backStackEntry ->
            val recipeId = backStackEntry.arguments
                ?.takeIf { it.containsKey("recipeId") }
                ?.getInt("recipeId")

            if (recipeId == null || recipeId <= 0) {
                navController.popBackStack()
            } else {
                RecipeDetailsScreen(
                    recipeId = recipeId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
