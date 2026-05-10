package com.example.simplifyweather.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.data.local.entity.FavoriteCity
import com.example.simplifyweather.ui.theme.CardColor
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun FavoritesItem(
    favorite: FavoriteCity,
    onClick: ()->Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(CardColor),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                favorite.cityName,
                fontSize = 20.sp,
                color = Dark_Text_Color,
                modifier = Modifier.weight(1f).offset(x = 10.dp)
            )
            IconButton(
                onClick = onClick
            ) {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = "deleteFavButton",
                    tint = Color.White
                )
            }
        }
    }
}