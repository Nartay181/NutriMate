package com.nutrimate.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutrimate.app.ui.components.NutriMateTopBar

@Composable
fun ProfileScreen(favoriteCount: Int, onThemeChanged: (Boolean) -> Unit = {}, viewModel: ProfileViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var showNameDialog by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().padding(horizontal = 20.dp)) {
        NutriMateTopBar("Profile")
        Column(Modifier.fillMaxWidth().weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(Modifier.height(12.dp))
            Box(Modifier.size(84.dp).clip(CircleShape).background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
                Text(state.name.take(1).uppercase(), style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onPrimary)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(state.name, style = MaterialTheme.typography.headlineSmall)
                IconButton({ showNameDialog = true }) { Icon(Icons.Default.Edit, "Edit name") }
            }
            Row(Modifier.fillMaxWidth().padding(vertical = 18.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                StatItem(favoriteCount.toString(), "Saved recipes")
                StatItem(state.viewedRecipes.toString(), "Recipes viewed")
            }
            HorizontalDivider()
            ProfileMenuItem("Appearance", "Light / Dark theme", checked = state.darkTheme) { viewModel.toggleTheme(); onThemeChanged(!state.darkTheme) }
            ProfileMenuItem("About NutriMate", "Simple recipes for every day") { }
            Row(Modifier.fillMaxWidth().padding(top = 20.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.primary)
                Text("Version 1.0", Modifier.padding(start = 12.dp), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
    if (showNameDialog) {
        var draft by remember(state.name) { mutableStateOf(state.name) }
        AlertDialog(onDismissRequest = { showNameDialog = false }, title = { Text("Your name") },
            text = { OutlinedTextField(draft, { draft = it }, label = { Text("Name") }, singleLine = true) },
            confirmButton = { TextButton({ viewModel.updateName(draft); showNameDialog = false }) { Text("Save") } },
            dismissButton = { TextButton({ showNameDialog = false }) { Text("Cancel") } })
    }
}

@Composable private fun StatItem(value: String, label: String) { Column(horizontalAlignment = Alignment.CenterHorizontally) { Text(value, style = MaterialTheme.typography.headlineSmall); Text(label, style = MaterialTheme.typography.bodySmall) } }
@Composable private fun ProfileMenuItem(title: String, subtitle: String, checked: Boolean? = null, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(title) },
        supportingContent = { Text(subtitle) },
        modifier = Modifier.fillMaxWidth(),
        trailingContent = {
            if (checked != null) Switch(checked = checked, onCheckedChange = { onClick() })
            else TextButton(onClick) { Text("Open") }
        }
    )
}
