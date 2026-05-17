package com.example.simplifyweather.data.remote

import com.google.gson.annotations.SerializedName

data class FiveDayWeatherResponse(
    val city: City,
    @SerializedName("list")
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