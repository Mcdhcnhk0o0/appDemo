package com.example.appdemo.activity.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

enum class LoginMode {
    Login, Sign
}


class LoginViewModel: ViewModel() {

    var loginMode by mutableStateOf(LoginMode.Login)

    var email by mutableStateOf("")
    var nickname by mutableStateOf("")
    var password by mutableStateOf("")

    fun getModeTip(): String = if (loginMode == LoginMode.Login) "去注册" else "去登录"

    fun getActionTip(): String = if (loginMode == LoginMode.Login) "登录" else "注册"

}