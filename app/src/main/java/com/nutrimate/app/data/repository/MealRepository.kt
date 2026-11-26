package com.nutrimate.app.data.repository

import com.nutrimate.app.data.remote.api.MealApiService
import com.nutrimate.app.data.remote.api.RetrofitClient
import com.nutrimate.app.data.remote.dto.CategoryDto
import com.nutrimate.app.data.remote.dto.MealDto

class MealRepository(
    private val api: MealApiService = RetrofitClient.mealApiService
) {
    suspend fun searchMeals(query: String): List<MealDto> {
        val response = api.searchMeals(query)
        return response.meals.orEmpty()
    }
    suspend fun getCategories(): List<CategoryDto> {
        val response = api.getCategories()
        return response.categories.orEmpty()
    }
    suspend fun filterByCategory(category: String): List<MealDto> {
        val response = api.filterByCategory(category)
        return response.meals.orEmpty()
    }
    suspend fun getMealById(mealId: String): MealDto? {
        val response = api.getMealById(mealId)
        return response.meals?.firstOrNull()
    }
}