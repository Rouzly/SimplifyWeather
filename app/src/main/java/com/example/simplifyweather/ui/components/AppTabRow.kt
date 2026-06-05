package com.example.simplifyweather.ui.components

import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AppTabRow(
    tabIndex: Int,
    tabs: List<String>,
    onTabSelected: (Int) -> Unit,
    contentColor: Color = Color.Cyan,
    containerColor: Color = Color.Transparent,
    modifier: Modifier = Modifier
) {
    TabRow(
        modifier = modifier,
        selectedTabIndex = tabIndex,
        containerColor = containerColor,
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