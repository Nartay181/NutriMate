package com.nutrimate.app.navigation

import androidx.compose.runtime.Composable
import com.nutrimate.app.ui.screens.details.MealDetailsScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nutrimate.app.ui.screens.home.HomeScreen
import com.nutrimate.app.ui.screens.details.RecipeDetailsScreen
import com.nutrimate.app.ui.screens.favorites.FavoritesScreen
import com.nutrimate.app.ui.screens.recipes.RecipesScreen
import com.nutrimate.app.viewmodel.SharedFavoritesViewModel

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val favoritesViewModel: SharedFavoritesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = AppRoutes.HOME
    ) {
        composable(AppRoutes.HOME) {
            HomeScreen(
                onOpenRecipes = { navController.navigate(AppRoutes.RECIPES) },
                onOpenFavorites = { navController.navigate(AppRoutes.FAVORITES) },
                onOpenRecipe = { recipeId ->
                    navController.navigate(AppRoutes.recipeDetails(recipeId))
                }
            )
        }
        composable(AppRoutes.RECIPES) {
            RecipesScreen(
                onRecipeClick = { mealId ->
                    navController.navigate(AppRoutes.mealDetails(mealId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoutes.FAVORITES) {
            FavoritesScreen(
                favoritesViewModel = favoritesViewModel,
                onRecipeClick = { recipeId ->
                    navController.navigate(AppRoutes.recipeDetails(recipeId))
                },
                onBack = { navController.popBackStack() },
                onExploreRecipes = {navController.navigate(AppRoutes.RECIPES)}
            )
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
                    favoritesViewModel = favoritesViewModel,
                    onBack = { navController.popBackStack() }
                )
            }
        }
        composable(
            route = AppRoutes.MEAL_DETAILS,
            arguments = listOf(
                navArgument("mealId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val mealId = backStackEntry.arguments?.getString("mealId")

            if (mealId.isNullOrBlank()) {
                navController.popBackStack()
            } else {
                MealDetailsScreen(
                    mealId = mealId,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}
