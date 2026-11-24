package com.nutrimate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nutrimate.app.navigation.AppNavGraph
import com.nutrimate.app.ui.theme.NutriMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var darkTheme by remember { mutableStateOf(false) }
            NutriMateTheme(darkTheme = darkTheme) {
                AppNavGraph(onThemeChanged = { darkTheme = it })
            }
        }
    }
}

