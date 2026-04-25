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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplifyweather.ui.viewmodel.FavoritesVeiwModel

@Composable
fun FavoritesScreen(
    favoritesViewModel: FavoritesVeiwModel = viewModel(factory = FavoritesVeiwModel.factory)
) {
    val favorites = favoritesViewModel.favorites.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp)
    ){
        LazyColumn(
            Modifier.fillMaxSize()
        ){
            itemsIndexed(
                items = favorites.value,
                key = {index, favorite -> favorite.cityName }
            ){index,favorite -> Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                Row(modifier = Modifier.weight(1f)) {
                    Text(favorite.cityName, fontSize = 15.sp)
                    IconButton(onClick = { favoritesViewModel.removeFavorite(favorite.cityName) }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "deleteFavButton"
                        )
                    }
                }
            } }
        }
    }
}