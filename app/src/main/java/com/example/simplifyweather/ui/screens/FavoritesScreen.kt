package com.example.simplifyweather.ui.screens

import android.graphics.ImageDecoder
import android.graphics.Paint
import android.hardware.lights.Light
import android.view.RoundedCorner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplifyweather.R
import com.example.simplifyweather.domain.model.WeatherType
import com.example.simplifyweather.ui.theme.CardColor
import com.example.simplifyweather.ui.theme.Clear
import com.example.simplifyweather.ui.theme.Clouds
import com.example.simplifyweather.ui.theme.Dark_Text_Color
import com.example.simplifyweather.ui.theme.Light_Text_Color
import com.example.simplifyweather.ui.theme.Mist
import com.example.simplifyweather.ui.theme.Rain
import com.example.simplifyweather.ui.theme.Snow
import com.example.simplifyweather.ui.theme.Thunderstorm
import com.example.simplifyweather.ui.viewmodel.FavoritesVeiwModel
import kotlinx.coroutines.launch
import okhttp3.internal.wait

@Composable
fun FavoritesScreen(
    navController: NavController,
    favoritesViewModel: FavoritesVeiwModel = viewModel(factory = FavoritesVeiwModel.factory)
) {
    val favorites = favoritesViewModel.favorites.collectAsState(initial = emptyList())
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState){ data ->
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
                    Modifier.fillMaxSize() .weight(1f)
                ) {
                    itemsIndexed(
                        items = favorites.value,
                        key = { index, favorite -> favorite.cityName }
                    ) { index, favorite ->
                        Row(
                            Modifier.fillMaxWidth()
                                .clickable(onClick = { navController.navigate("Main/${favorite.cityName}") }),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly,
                        ) {
                            Card(
                                modifier = Modifier.fillMaxWidth()
                                    .weight(1f)
                                    .padding(bottom = 10.dp),
                                shape = RoundedCornerShape(15.dp),
                                colors = CardDefaults.cardColors(CardColor),
                                elevation = CardDefaults.cardElevation(3.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        favorite.cityName,
                                        fontSize = 20.sp,
                                        color = Dark_Text_Color,
                                        modifier = Modifier.weight(1f).offset(x=10.dp)
                                    )
                                    IconButton(onClick = {
                                        scope.launch {
                                            val result = snackbarHostState.showSnackbar(
                                                "Удалить ${favorite.cityName}?",
                                                actionLabel = "Отмена",
                                                withDismissAction = false,
                                                duration = SnackbarDuration.Short
                                            )
                                            if (result == SnackbarResult.Dismissed) {
                                                favoritesViewModel.removeFavorite(favorite.cityName)
                                            }
                                        }
                                    }) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            contentDescription = "deleteFavButton",
                                            tint = Color.White
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(10.dp)
            ) {
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "backButton",
                    tint = Color.White
                )
            }
        }
    }
}