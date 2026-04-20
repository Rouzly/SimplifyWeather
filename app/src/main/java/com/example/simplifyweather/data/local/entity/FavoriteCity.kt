package com.example.simplifyweather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "favorite_cities")
data class FavoriteCity (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val cityName: String,
    val addedTimestamp: Long = System.currentTimeMillis()
)