package com.example.appdemo.activity.viewmodel

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.appdemo.activity.home.ui.content.ContentFragment
import com.example.appdemo.activity.home.ui.home.HomeFragment
import com.example.appdemo.activity.home.ui.mine.MineFragment
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

    companion object {
        val defaultItemModel = NavigationItemModel(id = "home", description = "首页", selectedIcon = Icons.Default.Home, unselectedIcon = Icons.Default.Face)

    }

    val cachedFragment: Map<String, Fragment> = mapOf(
        "home" to HomeFragment(),
        "content" to ContentFragment(),
        "mine" to MineFragment()
    )

    var firstLoad: Boolean = true

    var debugAddress by mutableStateOf("")

    var debugUrl by mutableStateOf(SharedPrefUtil.getDebugUrlAddress())

    var debugMode by mutableStateOf(false)

    var needRefresh by mutableStateOf(false)

    var selectedTabModel by mutableStateOf(defaultItemModel)

    val tabItems = listOf<NavigationItemModel>(
        defaultItemModel,
        NavigationItemModel(id = "content", description = "内容", selectedIcon = Icons.Default.Favorite, unselectedIcon = Icons.Default.FavoriteBorder),
        NavigationItemModel(id = "mine", description = "我的", selectedIcon = Icons.Default.Person, unselectedIcon = Icons.Default.AccountCircle)
    )

    fun getDebugModeDescription(): String {
        return if (debugMode) {
            "处于本地调试模式"
        } else {
            "处于远程调试模式"
        }
    }

}