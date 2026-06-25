package com.example.simplifyweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.domain.model.WeatherType
import com.example.simplifyweather.ui.theme.CardColor
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCard(
    onCardClick: () -> Unit,
    date: String,
    avgTemp: String,
    weatherType: WeatherType,
    minTemp: String,
    maxTemp: String
) {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM", Locale.ENGLISH)
    val dateObj = LocalDate.parse(date, inputFormatter)
    val formattedDate = dateObj.format(outputFormatter)

    val weatherId = when (weatherType) {
        is WeatherType.Clear -> 800
        is WeatherType.Clouds -> 804
        is WeatherType.Rain -> 500
        is WeatherType.Thunderstorm -> 200
        is WeatherType.Snow -> 600
        is WeatherType.Mist -> 700
        else -> 800
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .clickable { onCardClick() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(CardColor),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row {
                    Text(
                        text = "${maxTemp}°C  ",
                        fontSize = 20.sp,
                        color = Dark_Text_Color
                    )
                    Text(
                        text = "${minTemp}°C",
                        fontSize = 20.sp,
                        color = Dark_Text_Color.copy(alpha = 0.5f)
                    )
                }
                Text(
                    text = formattedDate,
                    fontSize = 15.sp,
                    color = Dark_Text_Color
                )
            }
            WeatherIcon(
                weatherId = weatherId,
                modifier = Modifier.size(60.dp)
            )
        }
    }
}