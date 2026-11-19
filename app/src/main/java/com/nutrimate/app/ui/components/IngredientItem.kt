package com.nutrimate.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IngredientItem(
    ingredient: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){}
}

@Preview(showBackground = true)
@Composable
fun IngredientItemPreview() {
    Column(modifier = Modifier.padding(16.dp)){
        IngredientItem(ingredient = "200g rice")
        IngredientItem(ingredient = "300g chicken")
        IngredientItem(ingredient = "1 onion")
    }
}