package com.example.appdemo.activity.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel


data class NavigationItemModel(
    val id: String,
    val description: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    // 为避免与Java反序列化框架交互异常，加一个无参构造函数
    constructor(): this("default", "default", Icons.Default.Info, Icons.Default.Info)
}


class HomeViewModel: ViewModel() {

    var selectedTabModel by mutableStateOf(NavigationItemModel())

}