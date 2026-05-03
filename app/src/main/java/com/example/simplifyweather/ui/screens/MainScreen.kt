package com.example.simplifyweather.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import com.example.simplifyweather.ui.theme.Light_Text_Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun MainScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.factory)
) {
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

    val textColor = when(weatherType){
        is WeatherType.Rain, is WeatherType.Thunderstorm, is WeatherType.Mist -> Dark_Text_Color
        is WeatherType.Clear, is WeatherType.Clouds, is WeatherType.Snow -> Light_Text_Color
        else -> Color.Gray
    }

    if (state is WeatherState.Success) {
        temperature = (state as WeatherState.Success).weather.main.temp.toInt().toString()
        cityName = (state as WeatherState.Success).weather.name
        val rawWeatherName = (state as WeatherState.Success).weather.weather[0].main
        weatherName = if (rawWeatherName == "Thunderstorm") "Stormy" else rawWeatherName
        countryName = Locale("", (state as WeatherState.Success).weather.sys.country).getDisplayCountry(Locale.ENGLISH)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Фон
        Box(
            modifier = Modifier
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

        if (state is WeatherState.Success) {
            when (weatherType) {
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
                        .offset(x = 70.dp, y = 70.dp)
                        .graphicsLayer { rotationZ = 25f }
                )
                is WeatherType.Rain -> Image(
                    painter = painterResource(R.drawable.rain_icon),
                    contentDescription = "rainIcon",
                    modifier = Modifier
                        .size(700.dp)
                        .align(Alignment.Center)
                        .offset(x = 35.dp, y = 100.dp)
                        .graphicsLayer { rotationZ = 40f }
                )
                is WeatherType.Thunderstorm -> Image(
                    painter = painterResource(R.drawable.lightning_icon),
                    contentDescription = "lightningIcon",
                    modifier = Modifier
                        .size(700.dp)
                        .align(Alignment.Center)
                        .offset(x = 100.dp, y = 200.dp)
                        .graphicsLayer { rotationZ = 70f }
                )
                is WeatherType.Snow -> Image(
                    painter = painterResource(R.drawable.snow_icon),
                    contentDescription = "snowIcon",
                    modifier = Modifier
                        .size(700.dp)
                        .align(Alignment.Center)
                        .offset(y = 70.dp)
                        .graphicsLayer { rotationZ = 90f }
                )
                is WeatherType.Mist -> Image(
                    painter = painterResource(R.drawable.fog_icon),
                    contentDescription = "fogIcon",
                    modifier = Modifier
                        .size(600.dp)
                        .align(Alignment.Center)
                        .offset(y = 25.dp)
                )
                else -> {}
            }

            val textOffsetX = when (weatherName) {
                "Rain" -> 20.dp
                "Clear" -> 35.dp
                "Clouds" -> 55.dp
                "Stormy" -> 55.dp
                "Snow" -> 35.dp
                else -> 25.dp
            }
            Text(
                weatherName,
                fontSize = 50.sp,
                fontFamily = FontFamily(Font(R.font.comfortaa)),
                color = textColor,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = textOffsetX, y = 240.dp)
                    .graphicsLayer { rotationZ = 90f }
            )
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
                TextField(
                    value = message.value,
                    textStyle = TextStyle(fontSize = 25.sp),
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            if (message.value.isNotBlank()) {
                                weatherViewModel.SearchWeather(message.value)
                            }
                        }
                    ),
                    onValueChange = { newText -> message.value = newText },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White.copy(alpha = 0.1f),
                        unfocusedContainerColor = Color.White.copy(alpha = 0.1f),
                        focusedTextColor = textColor,
                        unfocusedTextColor = textColor,
                        focusedIndicatorColor = textColor,
                        cursorColor = Color.White
                    )
                )
                IconButton(
                    onClick = {
                        if (message.value.isNotBlank()) {
                            weatherViewModel.addFavorite(message.value)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "toFavButton"
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.offset(x = 15.dp),
                    Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = formattedDate,
                        fontSize = 16.sp,
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.comfortaa)),
                    )
                    Text(
                        cityName,
                        fontSize = 30.sp,
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.comfortaa)),
                        )
                    Text(
                        text = countryName,
                        fontSize = 16.sp,
                        color = textColor,
                        fontFamily = FontFamily(Font(R.font.comfortaa)),
                        )
                }
                IconButton(onClick = { navController.navigate("Favorite") }) {
                    Icon(
                        Icons.Filled.FavoriteBorder,
                        contentDescription = "favButton"
                    )
                }
            }
            if (state is WeatherState.Success && temperature.isNotBlank()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .offset(x = 15.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            text = temperature,
                            color = textColor,
                            fontSize = 160.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_ace_regular)),
                            letterSpacing = (-10).sp
                        )
                        Text(
                            text = "°C",
                            color = textColor,
                            fontSize = 50.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_ace_regular)),
                            modifier = Modifier.offset(y = 25.dp)
                        )
                    }
                }
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