package com.example.simplifyweather.data.local.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.simplifyweather.data.local.dao.FavoriteCityDao
import com.example.simplifyweather.data.local.entity.FavoriteCity

@Database(
    entities = [
        FavoriteCity::class
    ],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun favoriteCityDao(): FavoriteCityDao
    companion object{
        fun createDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "test.db"
            ).build()
        }
    }
}