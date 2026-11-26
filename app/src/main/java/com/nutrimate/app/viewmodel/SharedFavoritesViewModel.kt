package com.nutrimate.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimate.app.data.local.AppDatabase
import com.nutrimate.app.data.local.FavoriteRecipeEntity
import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.data.repository.FavoritesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SharedFavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = FavoritesRepository(
        AppDatabase.getDatabase(application).favoriteRecipeDao()
    )
    val favorites: StateFlow<List<FavoriteRecipeEntity>> = repository.favorites
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
    val favoriteIds: StateFlow<Set<String>> = repository.favorites
        .map { list -> list.map { it.mealId }.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptySet()
        )

    // CREATE
    fun toggleFavorite(recipe: Recipe) {
        val mealId = recipe.id.toString()
        viewModelScope.launch {
            if (mealId in favoriteIds.value) {
                repository.removeFavorite(mealId)
            } else {
                repository.addFavorite(recipe)
            }
        }
    }

    // DELETE
    fun removeFavorite(mealId: String) {
        viewModelScope.launch {
            repository.removeFavorite(mealId)
        }
    }

    // UPDATE
    fun updateFavorite(favorite: FavoriteRecipeEntity, note: String, rating: Int) {
        viewModelScope.launch {
            repository.updateFavorite(favorite, note, rating)
        }
    }
}