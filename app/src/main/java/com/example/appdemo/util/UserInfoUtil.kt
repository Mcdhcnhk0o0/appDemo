package com.example.appdemo.util

object UserInfoUtil {

    fun getUserId(): String {
        return SharedPrefUtil.getUserIdCache()
    }

    fun getUserToken(): String {
        return SharedPrefUtil.getUserTokenCache()
    }

}