package com.nutrimate.app.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimate.app.data.model.Recipe
import com.nutrimate.app.data.remote.mapper.toRecipe
import com.nutrimate.app.data.repository.MealRepository
import com.nutrimate.app.data.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
sealed interface RecipeDetailsUiState {
    object Loading : RecipeDetailsUiState
    data class Success(val recipe: Recipe) : RecipeDetailsUiState
    data class Error(val message: String) : RecipeDetailsUiState
    object Empty : RecipeDetailsUiState
}

class RecipeDetailsViewModel : ViewModel() {

    private val mealRepository = MealRepository()

    private val _uiState = MutableStateFlow<RecipeDetailsUiState>(RecipeDetailsUiState.Loading)
    val uiState: StateFlow<RecipeDetailsUiState> = _uiState.asStateFlow()

    fun loadRecipe(recipeId: Int) {
        val localRecipe = RecipeRepository.getRecipeById(recipeId)
        if (localRecipe != null) {
            _uiState.value = RecipeDetailsUiState.Success(localRecipe)
            return
        }
        viewModelScope.launch {
            _uiState.value = RecipeDetailsUiState.Loading
            try {
                val meal = mealRepository.getMealById(recipeId.toString())
                _uiState.value = if (meal == null) {
                    RecipeDetailsUiState.Empty
                } else {
                    RecipeDetailsUiState.Success(meal.toRecipe())
                }
            }catch (e: IOException) { _uiState.value = RecipeDetailsUiState.Error("No internet connection")
            }catch (e: HttpException) {
                _uiState.value = RecipeDetailsUiState.Error("Server error: ${e.code()}")
            }
        }
    }
}