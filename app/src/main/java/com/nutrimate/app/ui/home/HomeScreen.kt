package com.nutrimate.app.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onOpenRecipes: () -> Unit,
    onOpenFavorites: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("NutriMate", style = MaterialTheme.typography.headlineMedium)
        Text(
            text = "Home screen placeholder",
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )
        Button(onClick = onOpenRecipes) { Text("Open Recipes") }
        Button(
            onClick = onOpenFavorites,
            modifier = Modifier.padding(top = 12.dp)
        ) { Text("Open Favorites") }
    }
}
