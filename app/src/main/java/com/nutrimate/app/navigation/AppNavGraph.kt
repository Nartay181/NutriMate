package com.nutrimate.app.navigation

import androidx.compose.runtime.Composable
import com.nutrimate.app.ui.screens.details.MealDetailsScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.nutrimate.app.ui.screens.home.HomeScreen
import com.nutrimate.app.ui.screens.details.RecipeDetailsScreen
import com.nutrimate.app.ui.screens.favorites.FavoritesScreen
import com.nutrimate.app.ui.screens.recipes.RecipesScreen
import com.nutrimate.app.viewmodel.SharedFavoritesViewModel
import com.nutrimate.app.ui.screens.profile.ProfileScreen
import com.nutrimate.app.ui.screens.settings.SettingsScreen
import com.nutrimate.app.ui.components.NutriMateBottomBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier

@Composable
fun AppNavGraph(onThemeChanged: (Boolean) -> Unit = {}) {
    val navController = rememberNavController()
    val favoritesViewModel: SharedFavoritesViewModel = viewModel()
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route
    val showBottomBar = currentRoute in listOf(AppRoutes.HOME, AppRoutes.RECIPES, AppRoutes.RECIPES_WITH_CATEGORY, AppRoutes.FAVORITES, AppRoutes.PROFILE)

    val navigateFromBottomBar: (String) -> Unit = { route ->
        if (route != currentRoute) {
            if (route == AppRoutes.HOME) {
                navController.popBackStack(AppRoutes.HOME, inclusive = false)
            } else {
                navController.navigate(route) {
                    popUpTo(AppRoutes.HOME) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    }
    Scaffold(bottomBar = { if (showBottomBar) NutriMateBottomBar(currentRoute, navigateFromBottomBar) }) { padding ->
    NavHost(navController = navController, startDestination = AppRoutes.HOME, modifier = androidx.compose.ui.Modifier.padding(padding)) {
        composable(AppRoutes.HOME) {
            HomeScreen(
                onOpenRecipes = { category -> navController.navigate(AppRoutes.recipes(category ?: "All")) },
                onOpenFavorites = { navController.navigate(AppRoutes.FAVORITES) },
                onOpenSettings = { navController.navigate(AppRoutes.SETTINGS) },
                onOpenRecipe = { recipeId ->
                    navController.navigate(AppRoutes.recipeDetails(recipeId))
                }
            )
        }
        composable(
            AppRoutes.RECIPES_WITH_CATEGORY,
            arguments = listOf(navArgument("category") { defaultValue = "All" })
        ) { entry ->
            RecipesScreen(
                onRecipeClick = { mealId ->
                    navController.navigate(AppRoutes.mealDetails(mealId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(AppRoutes.FAVORITES) {
            FavoritesScreen(
                onRecipeClick = { mealId ->
                    navController.navigate(AppRoutes.mealDetails(mealId))
                },
                onBack = { navController.popBackStack() },
                onExploreRecipes = {navController.navigate(AppRoutes.RECIPES)}
            )
        }
        composable(AppRoutes.PROFILE) { ProfileScreen(favoriteCount = favoritesViewModel.favoriteIds.collectAsState().value.size, onThemeChanged = onThemeChanged) }
        composable(AppRoutes.SETTINGS) { SettingsScreen(onBack = { navController.popBackStack() }) }
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
}
