package com.nutrimate.app.ui.screens.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutrimate.app.ui.components.MealCard
import com.nutrimate.app.ui.theme.NutriMateTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    onRecipeClick: (Int) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    recipesViewModel: RecipesViewModel = viewModel()
) {
    val searchQuery by recipesViewModel.searchQuery.collectAsState()
    val selectedCategory by recipesViewModel.selectedCategory.collectAsState()
    val uiState by recipesViewModel.uiState.collectAsState()
    val categories by recipesViewModel.categories.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipes") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = modifier.padding(innerPadding)) {
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

        when (val state = uiState) {
            is RecipesUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecipesUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.meals) {meal ->
                        MealCard(
                            meal = meal,
                            selectedCategory = selectedCategory
                        )
                    }
                }
            }

            is RecipesUiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error
                        )

                        Button(
                            onClick = {
                                recipesViewModel.loadRecipes()
                            }
                        ) {
                            Text(
                                text = "Retry"
                            )
                        }
                    }
                }
            }

            is RecipesUiState.Empty -> {
                Text(
                    text = "No recipes found",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
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
        RecipesScreen(onRecipeClick = {}, onBack = {})
    }
}
