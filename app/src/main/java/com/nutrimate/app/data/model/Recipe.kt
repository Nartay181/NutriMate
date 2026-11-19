package com.nutrimate.app.data.model

data class Recipe(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val category: String,
    val cookingTime: Int,
    val ingredients: List<String>,
    val instructions: List<String>
)
