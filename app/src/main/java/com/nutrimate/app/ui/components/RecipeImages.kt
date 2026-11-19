package com.nutrimate.app.ui.components

import com.nutrimate.app.R

fun getRecipeImage(recipeId: Int): Int? {
    return when (recipeId) {
        1 -> R.drawable.spaghetti_bolognese
        2 -> R.drawable.pancakes
        3 -> R.drawable.caesar_salad
        4 -> R.drawable.cheese_omelette
        else -> null
    }
}