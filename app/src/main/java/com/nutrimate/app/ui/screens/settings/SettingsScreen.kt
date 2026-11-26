package com.nutrimate.app.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nutrimate.app.ui.components.NutriMateTopBar

@Composable
fun SettingsScreen(onBack: () -> Unit, viewModel: ThemeViewModel = viewModel()) {
    val darkTheme by viewModel.darkTheme.collectAsStateWithLifecycle()
    Column(Modifier.fillMaxSize()) {
        NutriMateTopBar("Settings", onBack)
        Text("Preferences", Modifier.padding(20.dp), style = MaterialTheme.typography.titleLarge)
        ListItem(
            headlineContent = { Text("Appearance") },
            supportingContent = { Text(if (darkTheme) "Dark theme" else "Light theme") },
            trailingContent = { Switch(darkTheme, { viewModel.setDarkTheme(it) }) }
        )
    }
}
