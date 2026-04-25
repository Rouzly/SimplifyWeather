package com.example.simplifyweather.data.repository

import com.example.simplifyweather.data.local.entity.FavoriteCity
import com.example.simplifyweather.data.remote.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    suspend fun addFavorite(cityName: String): Boolean
    suspend fun removeFavorite(cityName: String)
    fun getFavorites(): Flow<List<FavoriteCity>>
    suspend fun isFavorite(cityName: String): Boolean
    suspend fun getWeather(cityName: String): WeatherResponse
}