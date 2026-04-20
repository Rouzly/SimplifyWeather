package com.example.simplifyweather

import android.app.Application
import com.example.simplifyweather.data.local.database.AppDatabase

class App: Application() {
    val database by lazy { AppDatabase.createDatabase((this)) }
}