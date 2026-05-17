package com.example.simplifyweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.simplifyweather.ui.screens.MainScreen
import com.example.simplifyweather.ui.theme.SimplifyWeatherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimplifyWeatherTheme {
                MainScreen(
                    navController = rememberNavController()
                )
            }
        }
    }
}