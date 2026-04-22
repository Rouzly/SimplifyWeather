package com.example.simplifyweather.ui.screens

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.util.TableInfo
import com.example.simplifyweather.ui.viewmodel.WeatherState
import com.example.simplifyweather.ui.viewmodel.WeatherViewModel

@Composable
fun MainScreen(
    weatherViewModel: WeatherViewModel = viewModel(factory = WeatherViewModel.factory)
) {
    val state by weatherViewModel.weatherState.collectAsState()
    val itemsList = weatherViewModel.itemList.collectAsState(initial = emptyList())
    val message = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TextField(
            value = message.value,
            textStyle = TextStyle(fontSize = 25.sp),
            onValueChange = { newText -> message.value = newText }
        )
        Spacer(modifier = Modifier.height(40.dp))
        val text = remember { mutableStateOf("Погода") }
        Button(onClick = { weatherViewModel.SearchWeather(message.value) }) {
            Text("Загрузить погоду", fontSize = 25.sp)
        }
        Spacer(modifier = Modifier.height(40.dp))
        Text(text.value, fontSize = 25.sp)
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

        }
        when (state) {
            is WeatherState.Loading -> CircularProgressIndicator()
            is WeatherState.Success -> {
                Text(text = (state as WeatherState.Success).weather.name)
                Text(text = (state as WeatherState.Success).weather.main.temp.toString())
            }
            is WeatherState.Error ->{
                (state as WeatherState.Error).message?.let { Text(text = it) }
            }

            else -> {}
        }
    }
}