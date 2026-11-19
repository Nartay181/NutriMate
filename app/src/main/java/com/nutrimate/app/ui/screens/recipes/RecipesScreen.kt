package com.nutrimate.app.ui.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutrimate.app.data.repository.RecipeRepository
import com.nutrimate.app.ui.components.RecipeCard
import com.nutrimate.app.ui.theme.NutriMateTheme

@Composable
fun RecipesScreen(
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    recipesViewModel: RecipesViewModel = viewModel()
) {
    val searchQuery by recipesViewModel.searchQuery.collectAsState()
    val selectedCategory by recipesViewModel.selectedCategory.collectAsState()
    val filteredRecipes by recipesViewModel.filteredRecipes.collectAsState()
    val categories = listOf(
        "All",
        "Breakfast",
        "Main Course",
        "Salad"
    )
    Column(modifier = modifier) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {recipesViewModel.onSearchQueryChange(it)},
            label = { Text(text = "Search recipes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = {
                        recipesViewModel.onCategoryChange(category)
                    },
                    label = {
                        Text(
                            text = category
                        )
                    }
                )
            }
        }

        if (filteredRecipes.isEmpty()) {
            Text(
                text = "No recipes found",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filteredRecipes) {recipe ->
                    RecipeCard(
                        recipe = recipe,
                        onClick = {
                            onRecipeClick(recipe.id)
                        },
                        modifier = Modifier.padding(
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    NutriMateTheme {
        RecipesScreen(onRecipeClick = {})
    }
}