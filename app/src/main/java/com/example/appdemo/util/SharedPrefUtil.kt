package com.example.appdemo.util

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtil {

    private const val SP_NAME = "main_sp"

    private const val USER_ID_KEY = "cached_user_id"
    private const val USER_TOKEN_KEY = "cached_user_token"
    private const val BASE_URL_SETTING_KEY = "base_url_setting"

    fun getSp(): SharedPreferences {
        return ApplicationUtil.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    private fun getStringByKey(key: String): String {
        return getSp().getString(key, "") ?: ""
    }

    fun getBaseUrlSetting(): String {
        return getSp().getString(BASE_URL_SETTING_KEY, "release") ?: "release"
    }

    fun isInDebugMode(): Boolean {
        return getBaseUrlSetting() == "debug"
    }

    fun setDebugBaseUrl() {
        getSp().edit().putString(BASE_URL_SETTING_KEY, "debug").apply()
    }

    fun setReleaseBaseUrl() {
        getSp().edit().putString(BASE_URL_SETTING_KEY, "release").apply()
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