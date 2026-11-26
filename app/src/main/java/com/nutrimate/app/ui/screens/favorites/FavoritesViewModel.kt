package com.nutrimate.app.ui.screens.favorites

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimate.app.data.local.AppDatabase
import com.nutrimate.app.data.local.FavoriteRecipeEntity
import com.nutrimate.app.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FavoritesRepository(AppDatabase.getDatabase(application).favoriteRecipeDao())
    val favorites: StateFlow<List<FavoriteRecipeEntity>> = repository.favorites.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList()
    )
    fun updateFavorite(favorite: FavoriteRecipeEntity, note: String, rating: Int) = viewModelScope.launch { repository.updateFavorite(favorite, note, rating) }
    fun removeFavorite(mealId: String) = viewModelScope.launch { repository.removeFavorite(mealId) }
}
