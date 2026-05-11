package com.example.simplifyweather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.R

@Composable
fun WeatherContent(
    formattedDate: String,
    cityName: String,
    countryName: String,
    temperature: String,
    textColor: Color,
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onAddToFavorites: () -> Unit,
    tabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
            TextField(
                value = searchText,
                textStyle = TextStyle(fontSize = 25.sp),
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.isNotBlank()) {
                            onSearch()
                        }
                    }
                ),
                onValueChange = { newText -> onSearchTextChange(newText) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White.copy(alpha = 0.1f),
                    unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                    focusedTextColor = textColor,
                    unfocusedTextColor = textColor,
                    focusedIndicatorColor = textColor,
                    cursorColor = Color.White
                )
            )
            IconButton(
                onClick = {
                    if (searchText.isNotBlank()) {
                        onAddToFavorites()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "toFavButton"
                )
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        AppTabRow(
            tabIndex = tabIndex,
            tabs = listOf("Main", "Favourite", "Week"),
            contentColor = textColor,
            onTabSelected = onTabSelected
        )
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.offset(x = 15.dp),
                Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = formattedDate,
                    fontSize = 16.sp,
                    color = textColor,
                    fontFamily = FontFamily(Font(R.font.comfortaa)),
                )
                Text(
                    cityName,
                    fontSize = 30.sp,
                    color = textColor,
                    fontFamily = FontFamily(Font(R.font.comfortaa)),
                )
                Text(
                    text = countryName,
                    fontSize = 16.sp,
                    color = textColor,
                    fontFamily = FontFamily(Font(R.font.comfortaa)),
                )
            }
        }
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = 15.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = temperature,
                    color = textColor,
                    fontSize = 160.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_ace_regular)),
                    letterSpacing = (-10).sp
                )
                Text(
                    text = "°C",
                    color = textColor,
                    fontSize = 50.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat_ace_regular)),
                    modifier = Modifier.offset(y = 25.dp)
                )
            }
        }
    }
}