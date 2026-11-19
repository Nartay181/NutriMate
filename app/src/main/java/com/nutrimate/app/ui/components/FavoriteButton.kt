package com.nutrimate.app.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun FavoriteButton(
    isFavorite: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
){
    IconButton(onClick = onClick,modifier=modifier) {
        Icon(
            imageVector = if(isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites"
        )
    }
}
@Preview(showBackground = true)
@Composable
fun FavoriteButtonPreview(){
    Row{
        FavoriteButton(isFavorite = true, onClick = {})
        FavoriteButton(isFavorite = false, onClick = {})
    }
}
