package com.nutrimate.app.data.remote.api

import com.nutrimate.app.data.remote.dto.CategoriesResponse
import com.nutrimate.app.data.remote.dto.MealFilterResponse
import com.nutrimate.app.data.remote.dto.MealSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {
    @GET("search.php")
    suspend fun searchMeals(
        @Query("s") query: String
    ): MealSearchResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun filterByCategory(
        @Query("c") category: String
    ): MealFilterResponse

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") mealId: String
    ): MealSearchResponse
}