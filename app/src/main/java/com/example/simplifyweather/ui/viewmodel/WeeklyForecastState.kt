package com.example.simplifyweather.ui.viewmodel

import com.example.simplifyweather.data.remote.FiveDayWeatherResponse
import com.example.simplifyweather.data.remote.WeatherResponse
import com.example.simplifyweather.domain.model.WeatherType


sealed class WeeklyForecastState {
    object Idle: WeeklyForecastState()
    object Loading : WeeklyForecastState()
    data class Success(val weather: FiveDayWeatherResponse): WeeklyForecastState()
    data class Error(val message: String?): WeeklyForecastState()
}