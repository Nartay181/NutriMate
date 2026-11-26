package com.nutrimate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nutrimate.app.navigation.AppNavGraph
import com.nutrimate.app.ui.theme.NutriMateTheme
import com.nutrimate.app.ui.screens.settings.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val darkTheme by themeViewModel.darkTheme.collectAsStateWithLifecycle()
            NutriMateTheme(darkTheme = darkTheme) {
                AppNavGraph(onThemeChanged = themeViewModel::setDarkTheme)
            }
        }
    }
}

