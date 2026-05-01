package com.example.simplifyweather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplifyweather.domain.model.WeatherType
import com.example.simplifyweather.ui.theme.Clear
import com.example.simplifyweather.ui.theme.Clouds
import com.example.simplifyweather.ui.theme.Mist
import com.example.simplifyweather.ui.theme.Rain
import com.example.simplifyweather.ui.theme.Snow
import com.example.simplifyweather.ui.theme.Thunderstorm
import com.example.simplifyweather.ui.viewmodel.WeatherState
import com.example.simplifyweather.ui.viewmodel.WeatherViewModel
import com.example.simplifyweather.R

@Composable
fun MainScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.factory)
) {
    val state by weatherViewModel.weatherState.collectAsState()
    val message = remember { mutableStateOf("") }
    val backStackEntry = navController.currentBackStackEntry
    val cityName = backStackEntry?.arguments?.getString("cityName") ?: ""
    var weatherName by remember{mutableStateOf("")}
    var temperature by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var windSpeed by remember { mutableStateOf("") }
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
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(
                when (weatherType) {
                    is WeatherType.Clear -> Clear
                    is WeatherType.Clouds -> Clouds
                    is WeatherType.Rain -> Rain
                    is WeatherType.Thunderstorm -> Thunderstorm
                    is WeatherType.Snow -> Snow
                    is WeatherType.Mist -> Mist
                    else -> Color.Gray
                }
            )
        )
        when(weatherType) {
            is WeatherType.Clear -> Image(
                painter = painterResource(R.drawable.sun_icon),
                contentDescription = "sunIcon",
                modifier = Modifier
                    .size(600.dp)
                    .align(Alignment.Center)
                    .offset(y = 25.dp)
            )
            is WeatherType.Clouds -> Image(
                painter = painterResource(R.drawable.cloud_icon),
                contentDescription = "cloudIcon",
                modifier = Modifier
                    .size(500.dp)
                    .align(Alignment.Center)
                    .offset(x = 70.dp, y = 30.dp)
                    .graphicsLayer{
                        rotationZ = 25f
                    }
            )
            is WeatherType.Rain -> Image(
                painter = painterResource(R.drawable.rain_icon),
                contentDescription = "rainIcon",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.Center)
                    .offset(x = 25.dp,y = 25.dp)
                    .graphicsLayer {
                        rotationZ = 40f
                    }
            )
            is WeatherType.Thunderstorm -> Image(
                painter = painterResource(R.drawable.lightning_icon),
                contentDescription = "lightningIcon",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.Center)
                    .offset(x = 100.dp,y = 120.dp)
                    .graphicsLayer {
                        rotationZ = 70f
                    }
            )
            is WeatherType.Snow -> Image(
                painter = painterResource(R.drawable.snow_icon),
                contentDescription = "lightningIcon",
                modifier = Modifier
                    .size(700.dp)
                    .align(Alignment.Center)
                    .offset(y = 50.dp)
                    .graphicsLayer {
                        rotationZ = 90f
                    }
            )
            is WeatherType.Mist -> Image(
                painter = painterResource(R.drawable.fog_icon),
                contentDescription = "sunIcon",
                modifier = Modifier
                    .size(600.dp)
                    .align(Alignment.Center)
                    .offset(y = 25.dp)
            )
            else -> {}
        }
        val textOffsetX = when (weatherName.length) {
            in 0..4 -> 30.dp
            else -> 70.dp
        }

        Text(
            weatherName.uppercase(),
            fontSize = 60.sp,
            fontFamily = FontFamily(Font(R.font.maian_font)),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = textOffsetX, y = 200.dp)
                .graphicsLayer { rotationZ = 90f }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                TextField(
                    value = message.value,
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions =  KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions =  KeyboardActions(onSearch = {if (message.value != "") {
                        weatherViewModel.SearchWeather(message.value)
                    }}),
                    onValueChange = { newText -> message.value = newText },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color.White
                    )
                )
                IconButton(onClick = { weatherViewModel.addFavorite(message.value) }) {
                    Icon(
                        imageVector = Icons.Filled.Star, contentDescription = "toFavButton"
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.navigate("Favorite")
                }) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = "favButton"
                    )
                }
            }
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "${temperature}°C",
                    fontSize = 64.sp,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }
            when (state) {
                is WeatherState.Loading -> CircularProgressIndicator()
                is WeatherState.Success -> {
                    Text(text = (state as WeatherState.Success).weather.name)
                    Text(text = (state as WeatherState.Success).weather.main.temp.toString())
                    temperature = (state as WeatherState.Success).weather.main.temp.toInt().toString()
                    humidity = (state as WeatherState.Success).weather.main.humidity.toString()
                    windSpeed = (state as WeatherState.Success).weather.wind.speed.toString()
                    val rawWeatherName = (state as WeatherState.Success).weather.weather[0].main
                    weatherName = if (rawWeatherName == "Thunderstorm") "Stormy" else rawWeatherName
                }

                is WeatherState.Error -> {
                    (state as WeatherState.Error).message?.let { Text(text = it) }
                }

                else -> {}
            }
        }
    }
}