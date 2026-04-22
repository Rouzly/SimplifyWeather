package com.example.simplifyweather.ui.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import com.example.simplifyweather.App
import com.example.simplifyweather.data.local.database.AppDatabase
import com.example.simplifyweather.data.remote.MainData
import com.example.simplifyweather.data.remote.Weather
import com.example.simplifyweather.data.remote.WeatherResponse
import com.example.simplifyweather.data.remote.Wind
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(database: AppDatabase): ViewModel() {
    val itemList = database.favoriteCityDao().getAllFavorites()
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()
    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return WeatherViewModel(database) as T
            }
        }
    }
    fun SearchWeather(city: String){
        _weatherState.value = WeatherState.Loading;
        viewModelScope.launch {
            //repository.getWeather(city)
            try{
                delay(1500)
                val fakeWeather = WeatherResponse(
                    name = "Москва",
                    main = MainData(
                        temp = 18.5,
                        feels_like = 17.0,
                        humidity = 65,
                        pressure = 1012
                    ),
                    weather = listOf(
                        Weather(
                            id = 800,
                            main = "Clear",
                            description = "ясно",
                            icon = "01d"
                        )
                    ),
                    wind = Wind(
                        speed = 3.5
                    )
                )
                _weatherState.value = WeatherState.Success(fakeWeather)
            }
            catch(e: Exception){
                _weatherState.value = WeatherState.Error(e.message)
            }
        }
    }
}