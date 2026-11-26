
package com.nutrimate.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = Color.White,
    secondary = GreenSecondary,
    onSecondary = Color.White,
    tertiary = OrangeAccent,
    onTertiary = Color.Black,
    background = LightBackground,
    onBackground = Color(0xFF1B211B),
    surface = LightSurface,
    onSurface = Color(0xFF1B211B)
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkGreenPrimary,
    onPrimary = Color(0xFF003909),
    secondary = DarkGreenSecondary,
    onSecondary = Color(0xFF103A17),
    tertiary = DarkOrangeAccent,
    onTertiary = Color(0xFF442A00),
    background = DarkBackground,
    onBackground = Color(0xFFE2E9E0),
    surface = DarkSurface,
    onSurface = Color(0xFFE2E9E0)
)

@Composable
fun NutriMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
