package com.nutrimate.app.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MealDto(
    @SerializedName("idMeal")
    val id: String,
    @SerializedName("strMeal")
    val name: String,
    @SerializedName("strMealThumb")
    val thumbnailUrl: String?,
    @SerializedName("strCategory")
    val category: String?,
    @SerializedName("strArea")
    val area: String?,
    @SerializedName("strInstructions")
    val instructions: String?,

    val strIngredient1: String? = null,
    val strIngredient2: String? = null,
    val strIngredient3: String? = null,
    val strIngredient4: String? = null,
    val strIngredient5: String? = null,
    val strIngredient6: String? = null,
    val strIngredient7: String? = null,
    val strIngredient8: String? = null,
    val strIngredient9: String? = null,
    val strIngredient10: String? = null,
    val strIngredient11: String? = null,
    val strIngredient12: String? = null,
    val strIngredient13: String? = null,
    val strIngredient14: String? = null,
    val strIngredient15: String? = null,
    val strIngredient16: String? = null,
    val strIngredient17: String? = null,
    val strIngredient18: String? = null,
    val strIngredient19: String? = null,
    val strIngredient20: String? = null,
    val strMeasure1: String? = null,
    val strMeasure2: String? = null,
    val strMeasure3: String? = null,
    val strMeasure4: String? = null,
    val strMeasure5: String? = null,
    val strMeasure6: String? = null,
    val strMeasure7: String? = null,
    val strMeasure8: String? = null,
    val strMeasure9: String? = null,
    val strMeasure10: String? = null,
    val strMeasure11: String? = null,
    val strMeasure12: String? = null,
    val strMeasure13: String? = null,
    val strMeasure14: String? = null,
    val strMeasure15: String? = null,
    val strMeasure16: String? = null,
    val strMeasure17: String? = null,
    val strMeasure18: String? = null,
    val strMeasure19: String? = null,
    val strMeasure20: String? = null
)


fun MealDto.getIngredients(): List<String> {

    val ingredients = listOf(
        strIngredient1, strIngredient2, strIngredient3,
        strIngredient4, strIngredient5, strIngredient6,
        strIngredient7, strIngredient8, strIngredient9,
        strIngredient10, strIngredient11, strIngredient12,
        strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18,
        strIngredient19, strIngredient20
    )

    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3,
        strMeasure4, strMeasure5, strMeasure6,
        strMeasure7, strMeasure8, strMeasure9,
        strMeasure10, strMeasure11, strMeasure12,
        strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18,
        strMeasure19, strMeasure20
    )

    return ingredients.mapIndexedNotNull { index, ingredient ->
        if (ingredient.isNullOrBlank()) {
            null
        } else {
            val measure = measures[index].orEmpty().trim()

            listOf(measure, ingredient.trim())
                .filter { it.isNotBlank() }
                .joinToString(" ")
        }
    }
}
