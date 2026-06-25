package com.example.simplifyweather.ui.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppTabRow(
    modifier: Modifier = Modifier,
    tabIndex: Int,
    tabs: List<String>,
    contentColor: Color,
    onTabSelected: (Int) -> Unit
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabIndex,
        containerColor = Color.Transparent,
        contentColor = contentColor
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = tabIndex == index,
                onClick = { onTabSelected(index) },
                text = { Text(title) }
            )
        }
    }
}