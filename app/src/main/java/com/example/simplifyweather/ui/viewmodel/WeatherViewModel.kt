package com.example.simplifyweather.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.layout.LastBaseline
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.simplifyweather.App
import com.example.simplifyweather.data.local.dao.LastCityDao
import com.example.simplifyweather.data.remote.FiveDayWeatherResponse
import com.example.simplifyweather.data.remote.RetrofitInstance
import com.example.simplifyweather.data.repository.WeatherRepository
import com.example.simplifyweather.data.repository.WeatherRepositoryImpl
import com.example.simplifyweather.domain.model.WeatherType.Clear.getWeatherType
import com.example.simplifyweather.ui.viewmodel.WeeklyForecastState.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository): ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Idle)
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()
    private val _weeklyForecastState = MutableStateFlow<WeeklyForecastState>(Idle)
    val weeklyForecastState: StateFlow<WeeklyForecastState> = _weeklyForecastState.asStateFlow()
    private val _selectedTabIndex = MutableStateFlow(0)
    private var isWeeklyForecastLoaded = false
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

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
                val LastCityDay = database.lastCityDao()
                val repository = WeatherRepositoryImpl(dao, LastCityDay, weatherApi)
                return WeatherViewModel(repository) as T
            }
        }
    }
    fun SearchWeather(city: String){
        _weatherState.value = WeatherState.Loading;
        resetWeeklyForecast()
        viewModelScope.launch {
            //repository.getWeather(city)
            try{
                delay(1500)
                val weather = repository.getWeather(city)
                val weatherId = weather.weather.firstOrNull()?.id ?: 0
                val weatherType = getWeatherType(weatherId)
                repository.saveLastCity(city)
                _weatherState.value = WeatherState.Success(weather, weatherType)
            }
            catch(e: Exception){
                _weatherState.value = WeatherState.Error(e.message)
            }
        }
    }
    fun loadWeeklyForecast(cityName: String) {
        if (isWeeklyForecastLoaded && _weeklyForecastState.value is Success) {
            return
        }
        _weeklyForecastState.value = Loading
        viewModelScope.launch {
            try {
                val weather = repository.getFiveDayForecast(cityName)
                _weeklyForecastState.value = Success(weather)
                isWeeklyForecastLoaded = true
            } catch (e: Exception) {
                _weeklyForecastState.value = Error(e.message)
            }
        }
    }
    fun addFavorite(cityName: String) {
        viewModelScope.launch {
            repository.addFavorite(cityName)
        }
    }
    fun selectTab(index: Int){
        _selectedTabIndex.value = index
    }
    fun loadLastCity(onCityLoaded: (String) -> Unit) {
        viewModelScope.launch {
            val city = repository.getLastCity()
            if (!city.isNullOrBlank()) {
                onCityLoaded(city)
            }
        }
    }
    fun resetWeeklyForecast() {
        isWeeklyForecastLoaded = false
        _weeklyForecastState.value = Idle
    }
}