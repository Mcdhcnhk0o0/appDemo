package com.example.appdemo.activity.viewmodel

import androidx.lifecycle.ViewModel

data class SettingItem(
    val description: String,
    val tip: String,
    val onClick: () -> Unit,
)

class SettingViewModel: ViewModel() {

    val settingList = mutableListOf<SettingItem>()

}