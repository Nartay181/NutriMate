package com.nutrimate.app.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutrimate.app.ui.components.PrimaryButton
import com.nutrimate.app.ui.components.RecipeCard
import com.nutrimate.app.ui.components.SectionTitle

@Composable
fun HomeScreen(onOpenRecipes: (String?) -> Unit, onOpenFavorites: () -> Unit, onOpenRecipe: (Int) -> Unit, viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)) {
        Spacer(Modifier.height(24.dp))
        Text(state.welcomeMessage, style = MaterialTheme.typography.headlineMedium)
        Text("What are you in the mood to cook today?", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Card(Modifier.fillMaxWidth().padding(top = 24.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer), shape = MaterialTheme.shapes.extraLarge) {
            Column(Modifier.padding(20.dp)) {
                Text("Find your next favourite recipe", style = MaterialTheme.typography.headlineSmall)
                Text("Fresh ideas, simple ingredients, happy meals.", Modifier.padding(top = 8.dp), style = MaterialTheme.typography.bodyMedium)
                PrimaryButton("Explore recipes", { onOpenRecipes(null) }, Modifier.padding(top = 16.dp))
            }
        }
        Row(Modifier.fillMaxWidth().padding(top = 28.dp), horizontalArrangement = Arrangement.SpaceBetween) { SectionTitle("Popular this week"); TextButton({ onOpenRecipes(null) }) { Text("View all") } }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp), contentPadding = PaddingValues(vertical = 8.dp)) { items(state.featuredRecipes, key = { it.id }) { recipe -> RecipeCard(recipe, { onOpenRecipe(recipe.id) }, Modifier.width(260.dp)) } }
        SectionTitle("Browse categories", Modifier.padding(top = 20.dp, bottom = 12.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) { items(listOf("Breakfast", "Chicken", "Vegetarian", "Dessert")) { label -> AssistChip(onClick = { onOpenRecipes(label) }, label = { Text(label) }) } }
        OutlinedButton(onClick = onOpenFavorites, modifier = Modifier.fillMaxWidth().padding(top = 20.dp, bottom = 28.dp)) { Text("Open your favorites") }
    }
}
