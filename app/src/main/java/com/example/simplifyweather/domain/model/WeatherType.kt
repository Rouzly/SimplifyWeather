package com.example.simplifyweather.domain.model

sealed class WeatherType {
    object Clear: WeatherType()
    object Clouds: WeatherType()
    object Rain: WeatherType()
    object Thunderstorm: WeatherType()
    object Snow: WeatherType()
    object Mist: WeatherType()
    object Unknown: WeatherType()

    fun getWeatherType(WeatherID: Int):WeatherType{
        return when(WeatherID){
            800 -> WeatherType.Clear
            in 801..804 -> WeatherType.Clouds
            in 300..531 -> WeatherType.Rain
            in 200..232 -> WeatherType.Thunderstorm
            in 600..622 -> WeatherType.Snow
            in 700..781 -> WeatherType.Mist
            else -> WeatherType.Unknown
        }
    }
}