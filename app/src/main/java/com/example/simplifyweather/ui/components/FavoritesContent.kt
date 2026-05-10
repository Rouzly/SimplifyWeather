package com.example.simplifyweather.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplifyweather.R
import com.example.simplifyweather.data.local.entity.FavoriteCity
import com.example.simplifyweather.ui.theme.CardColor
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import com.example.simplifyweather.ui.theme.Light_Text_Color
import com.example.simplifyweather.ui.viewmodel.FavoritesVeiwModel
import com.example.simplifyweather.ui.viewmodel.WeatherViewModel
import kotlinx.coroutines.launch

@Composable
fun FavoritesContent(
    favorites: List<FavoriteCity>,
    onClick: ()->Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
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
    { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().background(Light_Text_Color)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(10.dp)
            ) {
                Text(
                    "Favorite",
                    fontSize = 64.sp,
                    fontFamily = FontFamily(Font(R.font.comfortaa)),
                    color = Dark_Text_Color,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(15.dp))
                LazyColumn(
                    Modifier.fillMaxSize().weight(1f)
                ) {
                    itemsIndexed(
                        items = favorites,
                        key = { index, favorite -> favorite.cityName }
                    ) { index, favorite ->
                        Row(
                            Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            FavoritesItem(favorite, onClick)
                        }
                    }
                }
            }
        }
    }
}