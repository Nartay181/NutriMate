package com.nutrimate.app.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteRecipeDao {

    // READ
    @Query("SELECT * FROM favorite_recipes ORDER BY savedAt DESC")
    fun getAllFavorites(): Flow<List<FavoriteRecipeEntity>>

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteRecipeEntity)

    // UPDATE
    @Update
    suspend fun updateFavorite(favorite: FavoriteRecipeEntity)

    // DELETE
    @Delete
    suspend fun deleteFavorite(favorite: FavoriteRecipeEntity)

    // DELETE by mealId
    @Query("DELETE FROM favorite_recipes WHERE mealId = :mealId")
    suspend fun deleteFavoriteById(mealId: String)
}