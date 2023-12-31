package com.example.appdemo.activity.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.appdemo.util.SharedPrefUtil


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

    var debugAddress by mutableStateOf("")

    var debugUrl by mutableStateOf(SharedPrefUtil.getDebugUrlAddress())

    var debugMode by mutableStateOf(false)

    var needRefresh by mutableStateOf(false)

    var selectedTabModel by mutableStateOf(NavigationItemModel())

    fun getDebugModeDescription(): String {
        return if (debugMode) {
            "处于本地调试模式"
        } else {
            "处于远程调试模式"
        }
    }

}