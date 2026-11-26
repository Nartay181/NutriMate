package com.nutrimate.app.data.remote.mapper

import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.data.remote.dto.MealDto
fun MealDto.ingredientList(): List<String> {
    val ingredients = listOf(
        strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5,
        strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10,
        strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15,
        strIngredient16, strIngredient17, strIngredient18, strIngredient19, strIngredient20
    )
    val measures = listOf(
        strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
        strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10,
        strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15,
        strMeasure16, strMeasure17, strMeasure18, strMeasure19, strMeasure20
    )

    return ingredients.zip(measures)
        .filter { (ingredient, _) -> !ingredient.isNullOrBlank() }
        .map { (ingredient, measure) ->
            "${measure.orEmpty().trim()} ${ingredient.orEmpty().trim()}".trim()
        }
}
fun MealDto.toRecipe(): Recipe {
    return Recipe(
        id = id.toIntOrNull() ?: 0,
        name = name,
        description = area?.let { "$it cuisine" } ?: "",
        imageUrl = thumbnailUrl.orEmpty(),
        category = category.orEmpty(),
        cookingTime = 0,
        ingredients = ingredientList(),
        instructions = instructions.orEmpty()
            .lines()
            .map { it.trim() }
            .filter { it.isNotBlank() }
    )
}