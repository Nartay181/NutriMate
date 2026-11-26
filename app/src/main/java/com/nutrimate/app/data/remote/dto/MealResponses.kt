package com.nutrimate.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealSearchResponse(
    @SerializedName("meals")
    val meals: List<MealDto>?
)

data class MealFilterResponse(
    @SerializedName("meals")
    val meals: List<MealDto>?
)

data class CategoryDto(
    @SerializedName("idCategory")
    val id: String,
    @SerializedName("strCategory")
    val name: String,
    @SerializedName("strCategoryThumb")
    val thumbnailUrl: String?
)

data class CategoriesResponse(
    @SerializedName("categories")
    val categories: List<CategoryDto>?
)