package com.nutrimate.app.ui.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimate.app.data.remote.dto.MealDto
import com.nutrimate.app.data.repository.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import retrofit2.HttpException

sealed interface MealDetailsUiState {
    object Loading: MealDetailsUiState

    data class Success(
        val meal: MealDto
    ) : MealDetailsUiState

    data class Error(
        val message: String
    ) : MealDetailsUiState

    object NotFound : MealDetailsUiState
}

class MealDetailsViewModel : ViewModel() {
    private val mealRepository = MealRepository()
    private val _uiState = MutableStateFlow<MealDetailsUiState>(MealDetailsUiState.Loading)
    val uiState: StateFlow<MealDetailsUiState> = _uiState.asStateFlow()

    fun loadMeal(mealId: String) {
        viewModelScope.launch {
            _uiState.value = MealDetailsUiState.Loading

            try {
                val meal = mealRepository.getMealById(mealId)

                _uiState.value = if (meal != null) {
                    MealDetailsUiState.Success(meal)
                } else {
                    MealDetailsUiState.NotFound
                }
            } catch (e: IOException) {
                _uiState.value = MealDetailsUiState.Error(
                    "Please check your internet connection."
                )
            } catch (e: HttpException) {
                _uiState.value = MealDetailsUiState.Error(
                    "Server error: ${e.code()}"
                )
            }
        }
    }

}