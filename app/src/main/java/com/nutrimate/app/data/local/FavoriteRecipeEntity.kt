package com.nutrimate.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_recipes")
data class FavoriteRecipeEntity(
    @PrimaryKey
    val mealId: String,
    val name: String,
    val category: String,
    val imageUrl: String,
    val note: String = "",
    val rating: Int = 0,          // личная оценка 0–5, 0 = не оценено
    val savedAt: Long = System.currentTimeMillis()
)