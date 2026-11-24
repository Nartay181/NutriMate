package com.nutrimate.app.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.data.repository.RecipeRepository
import com.nutrimate.app.ui.components.EmptyState
import com.nutrimate.app.ui.components.FavoriteButton
import com.nutrimate.app.ui.components.IngredientItem
import com.nutrimate.app.ui.components.InstructionStep
import com.nutrimate.app.ui.components.getRecipeImage
import com.nutrimate.app.viewmodel.SharedFavoritesViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RecipeDetailsScreen(
    recipeId: Int,
    favoritesViewModel: SharedFavoritesViewModel,
    onBack: () -> Unit,
    detailsViewModel: RecipeDetailsViewModel = viewModel()
) {
    LaunchedEffect(recipeId) {
        detailsViewModel.loadRecipe(recipeId)
    }

    val uiState by detailsViewModel.uiState.collectAsState()
    val favoriteIds by favoritesViewModel.favoriteIds.collectAsState()
    val recipe = uiState.recipe
    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        recipe == null -> {
            EmptyState(message = "Recipe not found")
        }

        else -> {
            RecipeDetailsContent(
                recipe = recipe,
                isFavorite = recipe.id in favoriteIds,
                onFavoriteClick = { favoritesViewModel.toggleFavorite(recipe.id) },
                onBackClick = onBack
            ) }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsContent(
    recipe: Recipe,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(recipe.name) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    FavoriteButton(isFavorite = isFavorite, onClick = onFavoriteClick)
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                val imageRes = getRecipeImage(recipe.id)
                if (imageRes != null) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = recipe.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                Text(recipe.name, style = MaterialTheme.typography.headlineSmall)
                Text("${recipe.category} • ${recipe.cookingTime} min")
                Spacer(modifier = Modifier.height(8.dp))
                Text(recipe.description)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Ingredients", style = MaterialTheme.typography.titleLarge)
            }

            items(recipe.ingredients) { ingredient ->
                IngredientItem(ingredient = ingredient)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text("Instructions", style = MaterialTheme.typography.titleLarge)
            }

            itemsIndexed(recipe.instructions) { index, step ->
                InstructionStep(stepNumber = index + 1, text = step)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeDetailsScreenPreview() {
    RecipeDetailsContent(
        recipe = RecipeRepository.recipes.first(),
        isFavorite = false,
        onFavoriteClick = {},
        onBackClick = {}
    )
}