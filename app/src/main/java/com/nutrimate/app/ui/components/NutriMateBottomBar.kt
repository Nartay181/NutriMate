package com.nutrimate.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

private data class Destination(val route: String, val label: String, val icon: ImageVector)

@Composable
fun NutriMateBottomBar(currentRoute: String?, onNavigate: (String) -> Unit) {
    val destinations = listOf(
        Destination("home", "Home", Icons.Default.Home),
        Destination("recipes", "Recipes", Icons.Default.List),
        Destination("favorites", "Favorites", Icons.Default.Favorite),
        Destination("profile", "Profile", Icons.Default.Person)
    )
    NavigationBar {
        destinations.forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigate(destination.route) },
                icon = { Icon(destination.icon, destination.label) },
                label = { Text(destination.label) }
            )
        }
    }
}
