package com.example.appdemo.activity.home.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appdemo.pojo.vo.UserInfoVO

class UserViewModel: ViewModel() {

    var loginStatus by mutableStateOf(false)

    var loginUserModel by mutableStateOf(UserInfoVO())

}