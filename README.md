# NutriMate

NutriMate is an Android recipe and nutrition application for browsing local recipes, viewing recipe details, searching and filtering the recipe catalogue, and saving recipes to Favorites.

## Main features

- Home screen with feature card, popular recipes, categories, and Favorites shortcut
- Recipe catalogue with reusable recipe cards
- Search by recipe name
- Category filtering
- Recipe details with ingredients and instructions
- Navigation argument `recipeId: Int`
- Add and remove recipes from Favorites
- Empty states for unmatched searches and empty Favorites
- Material 3 styling and back navigation
- Shared bottom navigation across Home, Recipes, Favorites, and Profile
- Local Profile with editable name, statistics, and theme toggle
- DataStore-backed theme preference that survives app restarts
- Retrofit, Coil, and Room dependencies prepared for the API and Favorites modules

## Screens

Home, Recipes, Recipe Details, Favorites, and Profile. Recipe Details intentionally hides the bottom bar and uses a back action for a focused reading experience.

## Architecture

The application uses Kotlin, Jetpack Compose, MVVM, StateFlow, and Navigation Compose.

Requirements 8/9 extend the data flow with Retrofit for TheMealDB, Room for persistent Favorites, Coil for remote images, Kotlin Coroutines for asynchronous work, and DataStore for the theme preference.

```text
MainActivity
    ↓
NutriMateTheme
    ↓
AppNavGraph
    ↓
Compose screens
    ↓
ViewModels and StateFlow
    ↓
Local RecipeRepository
```

Shared recipe data is defined by the `Recipe` model and provided by repositories. Screen UI is separated from screen state and navigation logic. The planned data flow is:

```text
Compose screen -> ViewModel + StateFlow -> Repository
                                      ↙          ↘
                              Retrofit API       Room Favorites
                                      ↘
                                      Coil images

DataStore -> ThemeViewModel -> Material 3 theme
```

## Technologies

- Kotlin
- Android
- Jetpack Compose
- Material 3
- MVVM
- StateFlow
- Navigation Compose
- Local in-memory data

## Team

- Nartay Zhaksybay
- Alisher Dosmukhambet
- Akzholtay Chamen

## Run the project

1. Open the project in Android Studio.
2. Allow Gradle sync to complete.
3. Start an Android emulator or connect a device.
4. Run the `app` configuration.

The project can also be verified from the terminal:

```powershell
.\gradlew.bat test
.\gradlew.bat assembleDebug
```
