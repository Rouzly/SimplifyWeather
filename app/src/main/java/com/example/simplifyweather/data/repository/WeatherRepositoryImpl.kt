package com.example.simplifyweather.data.repository

import com.example.simplifyweather.data.local.dao.FavoriteCityDao
import com.example.simplifyweather.data.local.entity.FavoriteCity
import com.example.simplifyweather.data.remote.RetrofitInstance
import com.example.simplifyweather.data.remote.WeatherApi
import com.example.simplifyweather.data.remote.WeatherResponse
import com.example.simplifyweather.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.Flow

class WeatherRepositoryImpl(
    private val dao : FavoriteCityDao,
    private val Api: WeatherApi
): WeatherRepository{
    override suspend fun addFavorite(cityName: String): Boolean {
        if(dao.isFavorite(cityName)==0){
            dao.addFavorite(FavoriteCity(cityName = cityName))
            return true
        }
        else{return false}
    }

    override suspend fun removeFavorite(cityName: String) {
        if(dao.isFavorite(cityName) > 0){
            val city = dao.getFavoriteByCityName(cityName)
            city?.let { dao.removeFavorite(it) }
        }
    }

    override fun getFavorites(): Flow<List<FavoriteCity>> {
        return dao.getAllFavorites()
    }

    override suspend fun isFavorite(cityName: String): Boolean {
        if(dao.isFavorite(cityName) > 0) return true
        else return false
    }

    override suspend fun getWeather(cityName: String): WeatherResponse {
        return Api.getWeather(cityName, RetrofitInstance.API_KEY, "metric")
    }
}