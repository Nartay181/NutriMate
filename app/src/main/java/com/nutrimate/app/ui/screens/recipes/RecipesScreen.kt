package com.nutrimate.app.ui.screens.recipes

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
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
    LazyColumn(
        modifier = modifier
    ) {
        items(RecipeRepository.recipes) {recipe ->
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

@Preview(showBackground = true)
@Composable
fun RecipesScreenPreview() {
    NutriMateTheme {
        RecipesScreen(onRecipeClick = {})
    }
}