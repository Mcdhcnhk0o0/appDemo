package com.example.appdemo.activity.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.appdemo.network.ServiceCreator
import com.example.appdemo.util.SharedPrefUtil
import com.example.appdemo.util.ToastUtil
import com.example.appdemo.util.UserInfoUtil

class DebugViewModel: ViewModel() {

    var localIp by mutableStateOf(SharedPrefUtil.getLocalIPAddress())

    var remoteIp by mutableStateOf(SharedPrefUtil.getRemoteIPAddress() ?: "")

    var debugUrl by mutableStateOf(SharedPrefUtil.getLocalIPAddress())

    var debugMode by mutableStateOf(SharedPrefUtil.getBaseUrlSetting() == "debug")

    fun getDebugModeDescription(): String {
        return if (debugMode) {
            "处于本地调试模式"
        } else {
            "处于远程调试模式"
        }
    }

    fun changeLocalIP(targetIP: String) {
        if (targetIP.isNotBlank() && targetIP != localIp) {
            localIp = targetIP
            SharedPrefUtil.setLocalIPAddress(targetIP)
            ServiceCreator.refreshLocalIP(targetIP, true)
        }
    }

    fun changeRemoteIP(targetIP: String) {
        if (remoteIp.isNotBlank() && targetIP != remoteIp) {
            remoteIp = targetIP
            SharedPrefUtil.setRemoteIPAddress(targetIP)
            ServiceCreator.refreshRemoteIP(targetIP, true)
        }
    }

    fun changeEnvironment() {
        debugMode = !debugMode
        if (debugMode) {
            SharedPrefUtil.applyDebugUrlSetting()
        } else {
            SharedPrefUtil.applyReleaseUrlSetting()
        }
        ToastUtil.show("切换环境后请重新登陆")
        ServiceCreator.refreshToken(UserInfoUtil.getUserToken())
    }

}