package com.example.simplifyweather.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplifyweather.domain.model.WeatherType
import com.example.simplifyweather.ui.viewmodel.WeatherState
import com.example.simplifyweather.ui.viewmodel.WeatherViewModel
import com.example.simplifyweather.R
import com.example.simplifyweather.ui.components.AppTabRow
import com.example.simplifyweather.ui.components.FavoritesContent
import com.example.simplifyweather.ui.components.WeatherArt
import com.example.simplifyweather.ui.components.WeatherContent
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import com.example.simplifyweather.ui.theme.Light_Text_Color
import com.example.simplifyweather.ui.viewmodel.FavoritesVeiwModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.factory),
    favoriteViewModel: FavoritesVeiwModel = viewModel(factory = FavoritesVeiwModel.factory)
) {
    //weather
    val state by weatherViewModel.weatherState.collectAsState()
    val message = remember { mutableStateOf("") }
    val backStackEntry = navController.currentBackStackEntry
    var cityName = backStackEntry?.arguments?.getString("cityName") ?: ""
    var weatherName by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var countryName by remember { mutableStateOf("") }
    val currentDate = Date()
    val formatter = SimpleDateFormat("EEEE, dd MMM", Locale.ENGLISH)
    val formattedDate = formatter.format(currentDate)
    //favorite
    val tabIndex by weatherViewModel.selectedTabIndex.collectAsState()
    val favorites = favoriteViewModel.favorites.collectAsState(initial = emptyList()).value
    //forecast

    LaunchedEffect(cityName) {
        if (cityName.isNotBlank()) {
            message.value = cityName
            weatherViewModel.SearchWeather(cityName)
        }
    }
    val weatherType = when (val s = state) {
        is WeatherState.Success -> s.weatherType
        else -> WeatherType.Unknown
    }

    val textColor = when (weatherType) {
        is WeatherType.Rain, is WeatherType.Thunderstorm, is WeatherType.Mist -> Dark_Text_Color
        is WeatherType.Clear, is WeatherType.Clouds, is WeatherType.Snow -> Light_Text_Color
        else -> Color.Gray
    }

    if (state is WeatherState.Success) {
        temperature = (state as WeatherState.Success).weather.main.temp.toInt().toString()
        cityName = (state as WeatherState.Success).weather.name
        val rawWeatherName = (state as WeatherState.Success).weather.weather[0].main
        weatherName = if (rawWeatherName == "Thunderstorm") "Stormy" else rawWeatherName
        countryName = Locale(
            "",
            (state as WeatherState.Success).weather.sys.country
        ).getDisplayCountry(Locale.ENGLISH)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    containerColor = Color.Transparent,
                    snackbarData = data,
                    contentColor = Color.LightGray,
                    actionColor = Color.LightGray
                )
            }
        }
    )
    {
        Box(modifier = Modifier.fillMaxSize()) {
            WeatherArt(weatherType, textColor, weatherName)
        }
        Column(modifier = Modifier.fillMaxSize()) {
            when (tabIndex) {
                0 -> WeatherContent(
                    formattedDate,
                    cityName,
                    countryName,
                    temperature,
                    textColor,
                    searchText = message.value,
                    onSearchTextChange = { message.value = it },
                    onSearch = { if (message.value.isNotBlank()) weatherViewModel.SearchWeather(message.value) },
                    onAddToFavorites = { if (message.value.isNotBlank()) weatherViewModel.addFavorite(message.value)},
                    tabIndex = tabIndex,
                    onTabSelected = {index -> weatherViewModel.selectTab(index)},
                )

                1 -> FavoritesContent(
                    favorites = favorites,
                    onCityClick = { cityName ->
                        weatherViewModel.selectTab(0)
                        weatherViewModel.SearchWeather(cityName)
                        message.value = cityName
                    },
                    onRemove = {cityToRemove ->
                        scope.launch {
                            val result = snackbarHostState.showSnackbar(
                                "Удалить ${cityToRemove}?",
                                actionLabel = "Отмена",
                                withDismissAction = false,
                                duration = SnackbarDuration.Short
                            )
                            if (result == SnackbarResult.Dismissed) {
                                favoriteViewModel.removeFavorite(cityToRemove)
                            }
                        }
                    },
                    tabIndex,
                    onTabSelected = {index -> weatherViewModel.selectTab(index)}
                )
            }
        }
        if (state is WeatherState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (state is WeatherState.Error) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = (state as WeatherState.Error).message ?: "Ошибка загрузки")
            }
        }
    }
}