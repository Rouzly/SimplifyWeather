package com.example.simplifyweather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.ui.theme.Dark_Text_Color

@Composable
fun HourlyForecastCard(
    time: String,
    temperature: String,
    weatherId: Int
) {
    Row(
        modifier = Modifier.padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = time,
            fontSize = 14.sp,
            color = Dark_Text_Color
        )
        Spacer(Modifier.width(25.dp))
        Text(
            text = "${temperature}°C",
            fontSize = 14.sp,
            color = Dark_Text_Color
        )
        Spacer(Modifier.width(230.dp))
        WeatherIcon(
            weatherId = weatherId,
            modifier = Modifier.size(32.dp)
        )
    }
}