package com.nutrimate.app.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nutrimate.app.data.local.FavoriteRecipeEntity
import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.ui.components.EmptyState
import com.nutrimate.app.ui.components.FavoriteButton
import com.nutrimate.app.ui.components.RecipeCard
import com.nutrimate.app.viewmodel.SharedFavoritesViewModel

@Composable
fun FavoritesScreen(
    favoritesViewModel: SharedFavoritesViewModel,
    onRecipeClick: (Int) -> Unit,
    onBack: () -> Unit,
    onExploreRecipes: () -> Unit
) {
    val favorites by favoritesViewModel.favorites.collectAsState()

    FavoritesContent(
        favorites = favorites,
        onRecipeClick = onRecipeClick,
        onRemoveClick = { mealId -> favoritesViewModel.removeFavorite(mealId) },
        onSaveChanges = { favorite, note, rating ->
            favoritesViewModel.updateFavorite(favorite, note, rating)
        },
        onBackClick = onBack,
        onExploreRecipesClick = onExploreRecipes
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesContent(
    favorites: List<FavoriteRecipeEntity>,
    onRecipeClick: (Int) -> Unit,
    onRemoveClick: (String) -> Unit,
    onSaveChanges: (FavoriteRecipeEntity, String, Int) -> Unit,
    onBackClick: () -> Unit,
    onExploreRecipesClick: () -> Unit
) {
    var editingFavorite by remember { mutableStateOf<FavoriteRecipeEntity?>(null) }
    var noteText by remember { mutableStateOf("") }
    var ratingValue by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Your favorites") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        if (favorites.isEmpty()) {
            EmptyState(
                message = "No favorite recipes yet.\nTap ♥ on a recipe to save it.",
                modifier = Modifier.padding(innerPadding),
                actionText = "Explore Recipes",
                onAction = onExploreRecipesClick
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Recipes you want to cook again",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                items(favorites, key = { it.mealId }) { favorite ->
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RecipeCard(
                                recipe = favorite.toRecipe(),
                                onClick = { onRecipeClick(favorite.mealId.toIntOrNull() ?: 0) },
                                modifier = Modifier.weight(1f)
                            )
                            Column {
                                // DELETE
                                FavoriteButton(
                                    isFavorite = true,
                                    onClick = { onRemoveClick(favorite.mealId) }
                                )
                                // UPDATE
                                IconButton(onClick = {
                                    noteText = favorite.note
                                    ratingValue = favorite.rating
                                    editingFavorite = favorite
                                }) {
                                    Icon(Icons.Filled.Edit, contentDescription = "Edit note and rating")
                                }
                            }
                        }

                        if (favorite.rating > 0) {
                            Text(
                                text = stars(favorite.rating),
                                color = MaterialTheme.colorScheme.secondary,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                        }
                        if (favorite.note.isNotBlank()) {
                            Text(
                                text = "📝 ${favorite.note}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    val favoriteToEdit = editingFavorite
    if (favoriteToEdit != null) {
        AlertDialog(
            onDismissRequest = { editingFavorite = null },
            title = { Text(favoriteToEdit.name) },
            text = {
                Column {
                    Text("Your rating", style = MaterialTheme.typography.labelLarge)
                    Row {
                        for (star in 1..5) {
                            TextButton(onClick = { ratingValue = star }) {
                                Text(
                                    text = if (star <= ratingValue) "★" else "☆",
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = noteText,
                        onValueChange = { noteText = it },
                        label = { Text("Your note") },
                        placeholder = { Text("e.g. Add more garlic") }
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onSaveChanges(favoriteToEdit, noteText.trim(), ratingValue)
                    editingFavorite = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingFavorite = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}
private fun stars(rating: Int): String = "★".repeat(rating) + "☆".repeat(5 - rating)
private fun FavoriteRecipeEntity.toRecipe(): Recipe = Recipe(
    id = mealId.toIntOrNull() ?: 0,
    name = name,
    description = category,
    imageUrl = imageUrl,
    category = category,
    cookingTime = 0,
    ingredients = emptyList(),
    instructions = emptyList()
)

private val previewFavorites = listOf(
    FavoriteRecipeEntity(
        mealId = "52772",
        name = "Teriyaki Chicken Casserole",
        category = "Chicken",
        imageUrl = "",
        note = "Add more garlic",
        rating = 4
    ),
    FavoriteRecipeEntity(
        mealId = "52959",
        name = "Baked salmon with fennel",
        category = "Seafood",
        imageUrl = ""
    )
)

@Preview(showBackground = true)
@Composable
fun FavoritesScreenPreview() {
    FavoritesContent(
        favorites = previewFavorites,
        onRecipeClick = {},
        onRemoveClick = {},
        onSaveChanges = { _, _, _ -> },
        onBackClick = {},
        onExploreRecipesClick = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FavoritesScreenEmptyPreview() {
    FavoritesContent(
        favorites = emptyList(),
        onRecipeClick = {},
        onRemoveClick = {},
        onSaveChanges = { _, _, _ -> },
        onBackClick = {},
        onExploreRecipesClick = {}
    )
}