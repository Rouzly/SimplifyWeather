package com.example.simplifyweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.R
import com.example.simplifyweather.domain.model.WeatherType

@Composable
fun WeatherContent(
    formattedDate: String,
    cityName: String,
    countryName: String,
    temperature: String,
    textColor: Color,
    weatherType: WeatherType
) {
    Box(modifier = Modifier.fillMaxSize()) {
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