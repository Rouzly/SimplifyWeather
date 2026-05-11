package com.example.simplifyweather.ui.components

import android.R.attr.textColor
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.R
import com.example.simplifyweather.domain.model.WeatherType
import com.example.simplifyweather.ui.theme.Clear
import com.example.simplifyweather.ui.theme.Clouds
import com.example.simplifyweather.ui.theme.Mist
import com.example.simplifyweather.ui.theme.Rain
import com.example.simplifyweather.ui.theme.Snow
import com.example.simplifyweather.ui.theme.Thunderstorm

@Composable
fun WeatherArt(
    weatherType: WeatherType,
    textColor: Color,
    weatherName: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                when (weatherType) {
                    is WeatherType.Clear -> Clear
                    is WeatherType.Clouds -> Clouds
                    is WeatherType.Rain -> Rain
                    is WeatherType.Thunderstorm -> Thunderstorm
                    is WeatherType.Snow -> Snow
                    is WeatherType.Mist -> Mist
                    else -> Color.Gray
                }
            )
    ) {
        when (weatherType) {
            is WeatherType.Clear -> Image(
                painter = painterResource(R.drawable.sun_icon),
                contentDescription = "sunIcon",
                modifier = Modifier
                    .size(600.dp)
                    .align(Alignment.Center)
                    .offset(y = 25.dp)
            )

            is WeatherType.Clouds -> Image(
                painter = painterResource(R.drawable.cloud_icon),
                contentDescription = "cloudIcon",
                modifier = Modifier
                    .size(500.dp)
                    .align(Alignment.Center)
                    .offset(x = 70.dp, y = 70.dp)
                    .graphicsLayer { rotationZ = 25f }
            )

            is WeatherType.Rain -> Image(
                painter = painterResource(R.drawable.rain_icon),
                contentDescription = "rainIcon",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.Center)
                    .offset(x = 35.dp, y = 100.dp)
                    .graphicsLayer { rotationZ = 40f }
            )

            is WeatherType.Thunderstorm -> Image(
                painter = painterResource(R.drawable.lightning_icon),
                contentDescription = "lightningIcon",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.Center)
                    .offset(x = 100.dp, y = 200.dp)
                    .graphicsLayer { rotationZ = 70f }
            )

            is WeatherType.Snow -> Image(
                painter = painterResource(R.drawable.snow_icon),
                contentDescription = "snowIcon",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.Center)
                    .offset(y = 70.dp)
                    .graphicsLayer { rotationZ = 90f }
            )

            is WeatherType.Mist -> Image(
                painter = painterResource(R.drawable.fog_icon),
                contentDescription = "fogIcon",
                modifier = Modifier
                    .size(600.dp)
                    .align(Alignment.Center)
                    .offset(y = 25.dp)
            )

            else -> {}
        }
        val textOffsetX = when (weatherName) {
            "Rain" -> 20.dp
            "Clear" -> 35.dp
            "Clouds" -> 55.dp
            "Stormy" -> 55.dp
            "Snow" -> 35.dp
            else -> 25.dp
        }
        Text(
            weatherName,
            fontSize = 50.sp,
            fontFamily = FontFamily(Font(R.font.comfortaa)),
            color = textColor,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = textOffsetX, y = 240.dp)
                .graphicsLayer { rotationZ = 90f }
        )
    }
}