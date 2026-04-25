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
import kotlinx.coroutines.launch

class FavoritesVeiwModel(private val repository: WeatherRepository): ViewModel() {
    val favorites = repository.getFavorites()
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
                return FavoritesVeiwModel(repository) as T
            }
        }
    }
    fun removeFavorite(cityName: String){
        viewModelScope.launch {
            repository.removeFavorite(cityName)
        }
    }
}