package com.example.simplifyweather.ui.viewmodel

import com.example.simplifyweather.data.remote.WeatherResponse


sealed class WeatherState {
    object Idle: WeatherState()
    object Loading : WeatherState()
    data class Success(val weather: WeatherResponse): WeatherState()
    data class Error(val message: String?): WeatherState()
}