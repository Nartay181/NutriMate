package com.nutrimate.app.data.repository

import com.nutrimate.app.data.local.FavoriteRecipeDao
import com.nutrimate.app.data.local.FavoriteRecipeEntity
import com.nutrimate.app.data.model.Recipe
import kotlinx.coroutines.flow.Flow

class FavoritesRepository(
    private val favoriteRecipeDao: FavoriteRecipeDao
) {

    // READ
    val favorites: Flow<List<FavoriteRecipeEntity>> = favoriteRecipeDao.getAllFavorites()

    // CREATE
    suspend fun addFavorite(recipe: Recipe) {
        val favorite = FavoriteRecipeEntity(
            mealId = recipe.id.toString(),
            name = recipe.name,
            category = recipe.category,
            imageUrl = recipe.imageUrl
        )
        favoriteRecipeDao.insertFavorite(favorite)
    }
    // UPDATE
    suspend fun updateFavorite(favorite: FavoriteRecipeEntity, note: String, rating: Int) {
        favoriteRecipeDao.updateFavorite(
            favorite.copy(note = note, rating = rating)
        )
    }
    // DELETE
    suspend fun removeFavorite(mealId: String) {
        favoriteRecipeDao.deleteFavoriteById(mealId)
    }
}