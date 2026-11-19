package com.nutrimate.app.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.data.repository.RecipeRepository
import com.nutrimate.app.ui.components.EmptyState
import com.nutrimate.app.ui.components.FavoriteButton
import com.nutrimate.app.ui.components.RecipeCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    favoriteRecipes: List<Recipe>,
    onRecipeClick: (Int) -> Unit,
    onRemoveClick: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Favorites") })
        }
    ) { innerPadding ->
        if (favoriteRecipes.isEmpty()) {
            EmptyState(
                message = "No favorite recipes yet.\nTap ♥ on a recipe to save it.",
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(favoriteRecipes) { recipe ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RecipeCard(
                            recipe = recipe,
                            onClick = { onRecipeClick(recipe.id) },
                            modifier = Modifier.weight(1f)
                        )
                        FavoriteButton(
                            isFavorite = true,
                            onClick = { onRemoveClick(recipe.id) }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(
        favoriteRecipes = RecipeRepository.recipes.take(2),
        onRecipeClick = {},
        onRemoveClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenEmptyPreview() {
    FavoritesScreen(
        favoriteRecipes = emptyList(),
        onRecipeClick = {},
        onRemoveClick = {}
    )
}