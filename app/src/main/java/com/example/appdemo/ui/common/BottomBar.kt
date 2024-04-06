package com.example.appdemo.ui.common

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.appdemo.activity.viewmodel.HomeViewModel

@Composable
fun BottomBar(
    viewModel: HomeViewModel
) {
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        viewModel.tabItems.forEach { item ->
            val selected = item == viewModel.selectedTabModel
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    viewModel.selectedTabModel = item
                },
                label = {
                    Text(item.description, color = Color.Black)
                },
                icon = {
                    Icon(
                        imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }
            )
        }
    }
}