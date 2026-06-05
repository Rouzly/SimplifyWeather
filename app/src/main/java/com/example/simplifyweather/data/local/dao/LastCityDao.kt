package com.example.simplifyweather.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simplifyweather.data.local.entity.FavoriteCity
import com.example.simplifyweather.data.local.entity.LastCity
import kotlinx.coroutines.flow.Flow

@Dao
interface LastCityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLastCity(city: LastCity)

    @Query("SELECT * FROM last_city WHERE id = 1")
    suspend fun getLastCity(): LastCity?
}