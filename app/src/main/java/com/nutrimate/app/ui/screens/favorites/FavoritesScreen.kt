package com.nutrimate.app.ui.screens.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutrimate.app.data.local.FavoriteRecipeEntity
import com.nutrimate.app.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    onRecipeClick: (String) -> Unit,
    onBack: () -> Unit,
    onExploreRecipes: () -> Unit,
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val favorites by favoritesViewModel.favorites.collectAsStateWithLifecycle()
    Scaffold(topBar = {
        TopAppBar(title = { Text("Your favorites") }, navigationIcon = { IconButton(onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back") } })
    }) { padding ->
        if (favorites.isEmpty()) {
            EmptyState("Recipes you want to cook again", title = "Your favorites are empty", actionText = "Explore recipes", onAction = onExploreRecipes, modifier = Modifier.padding(padding))
        } else {
            LazyColumn(Modifier.padding(padding), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(favorites, key = { it.mealId }) { favorite ->
                    FavoriteRow(favorite, { onRecipeClick(favorite.mealId) }, { favoritesViewModel.removeFavorite(favorite.mealId) }) { note, rating -> favoritesViewModel.updateFavorite(favorite, note, rating) }
                }
            }
        }
    }
}

@Composable
private fun FavoriteRow(favorite: FavoriteRecipeEntity, onOpen: () -> Unit, onDelete: () -> Unit, onSave: (String, Int) -> Unit) {
    var editing by remember { mutableStateOf(false) }
    Card(onClick = onOpen, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text(favorite.name, style = MaterialTheme.typography.titleMedium); Text(favorite.category, style = MaterialTheme.typography.bodySmall) }
                IconButton(onDelete) { Icon(Icons.Default.Delete, "Remove") }
            }
            if (favorite.rating > 0) Text("Rating: ${favorite.rating}/5")
            if (favorite.note.isNotBlank()) Text(favorite.note, style = MaterialTheme.typography.bodyMedium)
            TextButton({ editing = true }) { Text("Edit note and rating") }
        }
    }
    if (editing) {
        var note by remember { mutableStateOf(favorite.note) }
        var rating by remember { mutableStateOf(favorite.rating.coerceIn(1, 5)) }
        AlertDialog(onDismissRequest = { editing = false }, title = { Text("Personal details") }, text = {
            Column { OutlinedTextField(note, { note = it }, label = { Text("Note") }); Text("Rating: $rating / 5"); Slider(rating.toFloat(), { rating = it.toInt().coerceIn(1, 5) }, valueRange = 1f..5f, steps = 3) }
        }, confirmButton = { TextButton({ onSave(note, rating); editing = false }) { Text("Save") } }, dismissButton = { TextButton({ editing = false }) { Text("Cancel") } })
    }
}
