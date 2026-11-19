package com.nutrimate.app.ui.screens.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nutrimate.app.data.repository.RecipeRepository
import com.nutrimate.app.ui.components.RecipeCard
import com.nutrimate.app.ui.theme.NutriMateTheme

@Composable
fun RecipesScreen(
    onRecipeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember {mutableStateOf("")}
    val filteredRecipes = RecipeRepository.recipes.filter { recipe ->
        recipe.name.contains(searchQuery, ignoreCase = true)
    }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text(text = "Search recipes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            singleLine = true
        )
        if (filteredRecipes.isEmpty()) {
            Text(
                text = "No recipes found",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            LazyColumn(
                modifier = modifier
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