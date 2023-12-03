package com.example.appdemo.util

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtil {

    private const val SP_NAME = "main_sp"

    private const val USER_ID_KEY = "cached_user_id"
    private const val USER_TOKEN_KEY = "cached_user_token"
    private const val DEBUG_ADDRESS_KEY = "debug_address"
    private const val BASE_URL_SETTING_KEY = "base_url_setting"

    fun getSp(): SharedPreferences {
        return ApplicationUtil.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    private fun getStringByKey(key: String, defaultValue: String = ""): String {
        return getSp().getString(key, "") ?: ""
    }

    fun getBaseUrlSetting(): String {
        return getSp().getString(BASE_URL_SETTING_KEY, "release") ?: "release"
    }

    fun isBaseUrlInDebugMode(): Boolean {
        return getBaseUrlSetting() == "debug"
    }

    fun applyDebugUrlSetting() {
        getSp().edit().putString(BASE_URL_SETTING_KEY, "debug").apply()
    }

    fun applyReleaseUrlSetting() {
        getSp().edit().putString(BASE_URL_SETTING_KEY, "release").apply()
    }

    fun getDebugUrlAddress(): String {
        return getStringByKey(DEBUG_ADDRESS_KEY, defaultValue = "http://192.168.0.102:8880/demo/")
    }

    fun setDebugUrlAddress(url: String) {
        getSp().edit().putString(DEBUG_ADDRESS_KEY, url).apply()
    }

    fun getUserTokenCache(): String {
        return getStringByKey(USER_TOKEN_KEY)
    }

    fun setUserTokenCache(token: String) {
        getSp().edit().putString(USER_TOKEN_KEY, token).apply()
    }

    fun getUserIdCache(): String {
        return getStringByKey(USER_ID_KEY)
    }

    fun setUserIdCache(userId: String) {
        getSp().edit().putString(USER_ID_KEY, userId).apply()
    }

}