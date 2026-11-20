package com.nutrimate.app.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SharedFavoritesViewModel: ViewModel() {
    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    fun toggleFavorite(recipeId: Int) {
        _favoriteIds.update { ids ->
            if (recipeId in ids) ids - recipeId else ids + recipeId
        }
    }
}