package com.nutrimate.app.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.nutrimate.app.data.remote.dto.getIngredients

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailsScreen(
    mealId: String,
    onBack: () -> Unit,
    detailsViewModel: MealDetailsViewModel = viewModel()
) {
    val uiState by detailsViewModel.uiState.collectAsState()

    LaunchedEffect(mealId) {
        detailsViewModel.loadMeal(mealId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipe Details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is MealDetailsUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is MealDetailsUiState.Success -> {
                    val meal = state.meal

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        AsyncImage(
                            model = meal.thumbnailUrl,
                            contentDescription = meal.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(240.dp)
                        )

                        Text(
                            text = meal.name,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Text(
                            text = meal.category ?: "Unknown category",
                            color = MaterialTheme.colorScheme.primary
                        )

                        Text(
                            text = "Ingredients",
                            style = MaterialTheme.typography.titleLarge
                        )

                        val ingredients = meal.getIngredients()

                        if (ingredients.isEmpty()) {
                            Text("No ingredients available.")
                        } else {
                            ingredients.forEach { ingredient ->
                                Text(
                                    text = "* $ingredient",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }

                        Text(
                            text = "Instructions",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Text(
                            text = formatInstructions(meal.instructions),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
                is MealDetailsUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = state.message
                        )

                        Button(onClick = {
                            detailsViewModel.loadMeal(mealId)
                        }) {
                            Text(
                                text = "Retry"
                            )
                        }
                    }
                }
                is MealDetailsUiState.NotFound -> {
                    Text(
                        text = "Recipe not found"
                    )
                }
            }
        }
    }
}



private fun formatInstructions(instructions: String?): String {
    if (instructions.isNullOrBlank()) {
        return "No instructions available."
    }

    val text = instructions.trim()
        .replace("\r\n", "\n")
        .replace("\r", "\n")

    // Examples: "1", "2.", "3)", "step 1"
    val standaloneNumber = Regex(
        """(?i)^\s*(?:step\s+)?\d{1,2}[.):]?\s*$"""
    )

    // Examples: "1. Mix ingredients", "step 2 Add water"
    val numberedText = Regex(
        """(?i)^\s*(?:step\s+\d{1,2}[.):]?|\d{1,2}[.)])\s+(.+)$"""
    )

    val steps = mutableListOf<String>()
    val currentStep = StringBuilder()
    var hasNumbering = false

    fun saveStep() {
        val content = currentStep.toString().trim()
        if (content.isNotEmpty()) {
            steps.add(content)
        }
        currentStep.clear()
    }

    for (line in text.lines()) {
        val trimmedLine = line.trim()

        val numberedMatch = numberedText.matchEntire(trimmedLine)

        when {
            standaloneNumber.matches(trimmedLine) -> {
                hasNumbering = true
                saveStep()
            }

            numberedMatch != null -> {
                hasNumbering = true
                saveStep()
                currentStep.append(numberedMatch.groupValues[1])
            }

            else -> {
                if (currentStep.isNotEmpty()) {
                    currentStep.append("\n")
                }
                currentStep.append(line)
            }
        }
    }

    saveStep()

    if (!hasNumbering) {
        return text.replace(Regex("\n{3,}"), "\n\n")
    }

    return steps.mapIndexed { index, step ->
        "${index + 1}. $step"
    }.joinToString("\n\n")
}

