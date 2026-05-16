package com.example.simplifyweather.data.remote

data class FiveDayWeatherResponse(
    val city: City,
    val forecasts: List<Forecast>
)

data class City(
    val name: String
)

data class Forecast(
    val dt_txt: String,
    val main: MainData,
    val weather: List<Weather>,
    val wind: Wind,
    val pop: Float,
    val visibility: Int
)