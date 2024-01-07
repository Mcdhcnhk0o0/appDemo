package com.example.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import com.example.home.model.HomeTabModel

object HomeTabConfig {

    private val defaultTabConfig = HomeTabModel(
        "home",
        "首页",
        Icons.Default.Home,
        Icons.Default.Face
    )

    private val contentTabConfig = HomeTabModel(
        "content",
        "内容",
        Icons.Default.Favorite,
        Icons.Default.FavoriteBorder
    )

    private val mineTabConfig = HomeTabModel(
        "mine",
        "我的",
        Icons.Default.Person,
        Icons.Default.AccountCircle
    )

    private val homeTabModels = listOf<HomeTabModel>(
        defaultTabConfig,
        contentTabConfig,
        mineTabConfig
    )

//    private val homeTabFragments =

    fun getHomeTabConfig(): List<HomeTabModel> {
        return homeTabModels
    }



}