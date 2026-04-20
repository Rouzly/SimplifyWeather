package com.example.simplifyweather.data.remote

data class WeatherResponse(
    val name: String,
    val main: MainData,
    val weather: List<Weather>,
    val wind: Wind
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