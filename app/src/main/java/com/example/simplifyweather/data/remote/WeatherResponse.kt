package com.example.simplifyweather.data.remote

import android.R

data class WeatherResponse(
    val name: String,
    val main: MainData,
    val weather: List<Weather>,
    val wind: Wind,
    val sys: Sys
)

data class MainData(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)

data class Sys(
    val type: Int,
    val id: Double,
    val country: String,
    val sunrise: Double,
    val sunset: Double
)