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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.HorizontalDivider

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

    when (val state = uiState) {
        is RecipeDetailsUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is RecipeDetailsUiState.Error -> {
            EmptyState(message = state.message)
        }
        is RecipeDetailsUiState.Empty -> {
            EmptyState(message = "Recipe not found")
        }
        is RecipeDetailsUiState.Success -> {
            val recipe = state.recipe
            RecipeDetailsContent(
                recipe = recipe,
                isFavorite = recipe.id in favoriteIds,
                onFavoriteClick = { favoritesViewModel.toggleFavorite(recipe.id) },
                onBackClick = onBack
            )
        }
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
        },
        bottomBar = {
            Button(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp)
                    .height(52.dp)
            ){
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isFavorite) "Remove from Favorites" else "Add to Favorites")
        }
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
                            .height(250.dp)
                            .clip(MaterialTheme.shapes.large),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                Text(
                    text = recipe.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = recipe.category,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "  •  ",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${recipe.cookingTime} min",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = recipe.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ingredients",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(recipe.ingredients) { ingredient ->
                IngredientItem(ingredient = ingredient)
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Instructions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
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

@Preview(showBackground = true)
@Composable
fun RecipeDetailsFavoritePreview(){
    RecipeDetailsContent(
        recipe = RecipeRepository.recipes.first(),
        isFavorite = true,
        onFavoriteClick = {},
        onBackClick = {}
    )
}