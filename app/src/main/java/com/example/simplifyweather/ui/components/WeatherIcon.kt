package com.example.simplifyweather.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.simplifyweather.R

@Composable
fun WeatherIcon(weatherId: Int, modifier: Modifier = Modifier) {
    val iconRes = when (weatherId) {
        800 -> R.drawable.ic_clear
        801 -> R.drawable.ic_few_clouds
        802 -> R.drawable.ic_scattered_clouds
        803, 804 -> R.drawable.ic_broken_clouds
        in 300..321 -> R.drawable.ic_drizzle
        in 500..531 -> R.drawable.ic_rain
        in 600..622 -> R.drawable.ic_snow
        in 200..232 -> R.drawable.ic_thunderstorm
       else -> R.drawable.ic_mist
    }
    Image(
        painter = painterResource(iconRes),
        contentDescription = null,
        modifier = modifier
    )
}