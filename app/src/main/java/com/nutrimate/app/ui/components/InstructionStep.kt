package com.nutrimate.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun InstructionStep(
    stepNumber: Int,
    text: String,
    modifier: Modifier = Modifier
){
    Row(modifier =modifier.padding(vertical = 6.dp)){
        Text(
            text = "$stepNumber",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1F)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun InstructionPreview(){
    Column(modifier = Modifier.padding(16.dp)){
        InstructionStep(1, "Wash the rice and soak it for 20 minutes")
        InstructionStep(2, "Fry the onion until golden")
        InstructionStep(3, "Add chicken and spices, cook for 10 minutes")
    }
}