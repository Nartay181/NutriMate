package com.nutrimate.app.ui.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileUiState(val name: String = "Alex", val viewedRecipes: Int = 0, val darkTheme: Boolean = false)

class ProfileViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()
    fun updateName(name: String) = _uiState.update { it.copy(name = name.ifBlank { it.name }) }
    fun toggleTheme() = _uiState.update { it.copy(darkTheme = !it.darkTheme) }
}
