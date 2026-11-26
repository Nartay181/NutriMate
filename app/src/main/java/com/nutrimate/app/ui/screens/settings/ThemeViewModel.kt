package com.nutrimate.app.ui.screens.settings

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimate.app.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SettingsRepository(application)
    val darkTheme: StateFlow<Boolean> = repository.darkTheme.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), false
    )
    fun setDarkTheme(enabled: Boolean) = viewModelScope.launch { repository.setDarkTheme(enabled) }
}
