package com.example.simplifyweather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.R
import com.example.simplifyweather.data.remote.Forecast
import com.example.simplifyweather.domain.model.WeatherType.Clear.getWeatherType
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import com.example.simplifyweather.ui.theme.Light_Text_Color

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekContent(
    forecasts: List<Forecast>,
    cityName: String,
) {
    val grouped = forecasts.groupBy { it.dt_txt.substring(0, 10) }
    val days = grouped.toList().sortedBy { it.first }
    var expandedDate by remember { mutableStateOf<String?>(null) }

    Box(modifier = Modifier.fillMaxSize().background(Light_Text_Color)) {
        Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                cityName,
                fontSize = 40.sp,
                fontFamily = FontFamily(Font(R.font.comfortaa)),
                color = Dark_Text_Color
            )
            Spacer(modifier = Modifier.height(70.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(
                    items = days,
                    key = { (date, _) -> date }
                ) { (date, forecastsForDay) ->
                    val avgTemp = forecastsForDay.map { it.main.temp }.average().toInt().toString()
                    val minTemp = forecastsForDay.minOf { it.main.temp }.toInt().toString()
                    val maxTemp = forecastsForDay.maxOf { it.main.temp }.toInt().toString()
                    val mainForecast = forecastsForDay.firstOrNull { it.dt_txt.contains("12:00") }
                        ?: forecastsForDay.first()
                    val weatherId = mainForecast.weather.first().id
                    val weatherType = getWeatherType(weatherId)

                    DayCard(
                        onCardClick = { expandedDate = if (expandedDate == date) null else date },
                        date = date,
                        avgTemp = avgTemp,
                        weatherType = weatherType,
                        minTemp = minTemp,
                        maxTemp = maxTemp
                    )

                    if (expandedDate == date) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                        ) {
                            forecastsForDay.forEach { forecast ->
                                val forecastWeatherId = forecast.weather.first().id
                                HourlyForecastCard(
                                    time = forecast.dt_txt.substring(11, 16),
                                    temperature = forecast.main.temp.toInt().toString(),
                                    weatherId = forecastWeatherId
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}