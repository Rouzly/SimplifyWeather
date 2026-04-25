package com.example.simplifyweather.ui.screens

import android.graphics.ImageDecoder
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.simplifyweather.ui.viewmodel.FavoritesVeiwModel
import kotlinx.coroutines.launch

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
                    snackbarData = data,
                    containerColor = Color.DarkGray,
                    contentColor = Color.LightGray,
                    actionColor = Color.LightGray
                )
            }
        }
    )
    { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(10.dp)
        ) {
            LazyColumn(
                Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    items = favorites.value,
                    key = { index, favorite -> favorite.cityName }
                ) { index, favorite ->
                    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        Row(modifier = Modifier.weight(1f)) {
                            Text(favorite.cityName, fontSize = 15.sp)
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
                                    contentDescription = "deleteFavButton"
                                )
                            }
                        }
                    }
                }
            }
        }
            IconButton(onClick = {navController.navigate("Main")},
                modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(10.dp)){
                Icon(
                    Icons.Filled.Close,
                    contentDescription = "backButton"
                )
            }
        }
    }
}