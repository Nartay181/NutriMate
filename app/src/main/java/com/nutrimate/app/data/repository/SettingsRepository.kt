package com.nutrimate.app.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.settingsDataStore by preferencesDataStore(name = "nutrimate_settings")

class SettingsRepository(private val context: Context) {
    private val darkThemeKey = booleanPreferencesKey("dark_theme")
    val darkTheme: Flow<Boolean> = context.settingsDataStore.data.map { preferences ->
        preferences[darkThemeKey] ?: false
    }
    suspend fun setDarkTheme(enabled: Boolean) {
        context.settingsDataStore.edit { preferences -> preferences[darkThemeKey] = enabled }
    }
}
