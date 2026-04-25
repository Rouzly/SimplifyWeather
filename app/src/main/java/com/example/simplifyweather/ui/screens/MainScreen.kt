package com.example.simplifyweather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplifyweather.ui.viewmodel.WeatherState
import com.example.simplifyweather.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@Composable
fun MainScreen(
    navController: NavController,
    weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.factory)
) {
    val state by weatherViewModel.weatherState.collectAsState()
    val message = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(15.dp)) {
            TextField(
                value = message.value,
                textStyle = TextStyle(fontSize = 25.sp),
                onValueChange = { newText -> message.value = newText }
            )
            IconButton(onClick = { weatherViewModel.addFavorite(message.value) }) {
                Icon(
                    imageVector = Icons.Filled.Star, contentDescription = "toFavButton"
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        val text = remember { mutableStateOf("Погода") }
        Button(onClick = {
            if (message.value != "") {
                weatherViewModel.SearchWeather(message.value)
            }
        }) {
            Text("Загрузить погоду", fontSize = 25.sp)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(text.value, fontSize = 25.sp)
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
        when (state) {
            is WeatherState.Loading -> CircularProgressIndicator()
            is WeatherState.Success -> {
                Text(text = (state as WeatherState.Success).weather.name)
                Text(text = (state as WeatherState.Success).weather.main.temp.toString())
            }

            is WeatherState.Error -> {
                (state as WeatherState.Error).message?.let { Text(text = it) }
            }

            else -> {}
        }
    }
}