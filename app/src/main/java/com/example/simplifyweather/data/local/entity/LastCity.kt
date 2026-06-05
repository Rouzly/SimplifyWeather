package com.example.simplifyweather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Last_City")
data class LastCity (
    @PrimaryKey
    val id: Int = 1,
    val cityName: String = "",
)
