package com.example.simplifyweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simplifyweather.App
import com.example.simplifyweather.R
import com.example.simplifyweather.data.remote.Forecast
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import com.example.simplifyweather.ui.theme.Light_Text_Color

@Composable
fun WeekContent(
    forecasts: List<Forecast>,
    cityName: String,
    tabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize().background(Light_Text_Color)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                AppTabRow(
                    tabIndex = tabIndex,
                    tabs = listOf("Main", "Favourite", "Week"),
                    contentColor = Dark_Text_Color,
                    onTabSelected = onTabSelected
                )
                LazyColumn(
                    Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = forecasts,
                        key = { _, forecast -> forecast.dt_txt }
                    ) { _, forecast ->
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Text(
                                forecast.main.temp.toString(),
                                fontSize = 64.sp,
                                fontFamily = FontFamily(Font(R.font.comfortaa)),
                                color = Dark_Text_Color,
                            )
                        }
                    }
                }
            }
        }
    }
}