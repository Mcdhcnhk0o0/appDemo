package com.example.appdemo.util

import android.provider.Settings

object DeviceInfoUtil {

    fun getAndroidId(): String {
        return Settings.Secure.getString(ApplicationUtil.getApplicationContext().contentResolver, Settings.Secure.ANDROID_ID)
    }

}