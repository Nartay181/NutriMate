package com.nutrimate.app.data.repository

import com.nutrimate.app.data.model.Recipe

object RecipeRepository {
    val recipes = listOf(
        Recipe(
            id = 1,
            name = "Spaghetti Bolognese",
            description = "Classic Italian pasta with a rich meat and tomato sauce.",
            imageUrl = "",
            category = "Main Course",
            cookingTime = 35,
            ingredients = listOf(
                "200g spaghetti",
                "200g ground beef",
                "200g tomato sauce",
                "1 onion",
                "2 claves of garlic",
                "1 tbsp olive oil",
                "Salt and pepper"
            ),
            instructions = listOf(
                "Boil the spaghetti according to the package instructions.",
                "Chop the onion and garlic.",
                "Heat olive oil in a pan and cook the onion and garlic.",
                "Add ground beef and cook until browned.",
                "Add tomato sauce, salt and pepper. Simmer for 10 minutes.",
                "Serve the sauce over the spaghetti."
            )
        ),
        Recipe(
            id = 2,
            name = "Pancakes",
            description = "Soft and fluffy pancakes for a delicious breakfast.",
            imageUrl = "",
            category = "Breakfast",
            cookingTime = 20,
            ingredients = listOf(
                "150g flour",
                "1 egg",
                "200ml milk",
                "1 tbsp sugar",
                "1 tsp baking powder",
                "Butter"
            ),
            instructions = listOf(
                "Mix the flour, sugar, and baking powder.",
                "Add the egg and milk, then mix until smooth.",
                "Heat a frying pan with a little butter.",
                "Pour small portions of batter into the pan.",
                "Cook each pancake on both sides until golden brown.",
                "Serve warm."
            )
        ),
        Recipe(
            id = 3,
            name = "Caesar Salad",
            description = "Fresh salad with chicken, lettuce, croutons, and Caesar dressing.",
            imageUrl = "",
            category = "Salad",
            cookingTime = 25,
            ingredients = listOf(
                "200g chicken breast",
                "1 romaine lettuce",
                "50g croutons",
                "30g Parmesan cheese",
                "3 tbsp Caesar dressing",
                "1 tbsp olive oil"
            ),
            instructions = listOf(
                "Cook the chicken breast thoroughly.",
                "Wash and chop the lettuce.",
                "Slice the cooked chicken.",
                "Combine lettuce, chicken and croutons.",
                "Add Parmesan cheese and Caesar dressing.",
                "Mix gently and serve."
            )
        ),
        Recipe(
            id = 4,
            name = "Cheese Omelette",
            description = "A quick and tasty omelette with melted cheese.",
            imageUrl = "",
            category = "Breakfast",
            cookingTime = 10,
            ingredients = listOf(
                "2 eggs",
                "30g cheese",
                "2 tbsp milk",
                "1 tsp butter",
                "Salt and pepper"
            ),
            instructions = listOf(
                "Crack the eggs into a bowl.",
                "Add milk, salt, and pepper, then whisk.",
                "Melt butter in a frying pan.",
                "Pour in the egg mixture.",
                "Add cheese and cook until the eggs are set.",
                "Fold the omelette and serve."
            )
        )
    )
    fun getRecipeById(id: Int): Recipe? {
        return recipes.find { recipe ->
            recipe.id == id
        }
    }
}