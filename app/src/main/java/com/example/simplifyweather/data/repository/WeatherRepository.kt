package com.example.simplifyweather.data.repository

import android.R
import androidx.constraintlayout.helper.widget.Flow
import com.example.simplifyweather.data.local.dao.FavoriteCityDao

interface WeatherRepository {
    suspend fun addFavoite(id: String): String
    suspend fun removeFavorite(data: String)
    suspend fun getFavorites(): List<String> {

        return FavoriteCityDao.getAllFavorites()
    }
    fun isFavorite(city: R.string): R.bool
}