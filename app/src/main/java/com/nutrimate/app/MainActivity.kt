package com.nutrimate.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nutrimate.app.navigation.AppNavGraph
import com.nutrimate.app.ui.theme.NutriMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NutriMateTheme {
                AppNavGraph()
            }
        }
    }
}

