package com.example.simplifyweather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.simplifyweather.data.local.entity.FavoriteCity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {
    @Insert
    suspend fun addFavorite(city: FavoriteCity)

    @Delete
    suspend fun removeFavorite(city: FavoriteCity)

    @Query("SELECT * FROM favorite_cities ORDER BY addedTimestamp DESC")
    fun getAllFavorites(): Flow<List<FavoriteCity>>

    @Query("SELECT COUNT(*) FROM favorite_cities WHERE cityName = :cityName")
    suspend fun isFavorite(cityName: String): Int
}