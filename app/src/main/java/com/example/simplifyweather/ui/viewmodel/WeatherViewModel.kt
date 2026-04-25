package com.example.simplifyweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.simplifyweather.App
import com.example.simplifyweather.data.remote.RetrofitInstance
import com.example.simplifyweather.data.repository.WeatherRepository
import com.example.simplifyweather.data.repository.WeatherRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
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
                val dao = database.favoriteCityDao();
                val weatherApi = RetrofitInstance.api
                val repository = WeatherRepositoryImpl(dao, weatherApi)
                return WeatherViewModel(repository) as T
            }
        }
    }
    fun SearchWeather(city: String){
        _weatherState.value = WeatherState.Loading;
        viewModelScope.launch {
            //repository.getWeather(city)
            try{
                delay(1500)
                val weather = repository.getWeather(city)
                _weatherState.value = WeatherState.Success(weather)
            }
            catch(e: Exception){
                _weatherState.value = WeatherState.Error(e.message)
            }
        }
    }
    fun addFavorite(cityName: String) {
        viewModelScope.launch {
            repository.addFavorite(cityName)
        }
    }
}