package com.example.simplifyweather.ui.viewmodel

import com.example.simplifyweather.data.remote.WeatherResponse
import com.example.simplifyweather.domain.model.WeatherType


sealed class WeatherState {
    object Idle: WeatherState()
    object Loading : WeatherState()
    data class Success(val weather: WeatherResponse, val weatherType: WeatherType): WeatherState()
    data class Error(val message: String?): WeatherState()
}