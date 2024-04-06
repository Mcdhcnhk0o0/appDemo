package com.example.appdemo.util

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtil {

    private const val SP_NAME = "main_sp"

    private const val USER_ID_KEY = "cached_user_id"
    private const val USER_TOKEN_KEY = "cached_user_token"
    private const val BASE_URL_SETTING_KEY = "base_url_setting"

    private const val LOCAL_IP_KEY = "local_ip_key"
    private const val REMOTE_IP_KEY = "remote_ip_key"

    private fun getSp(): SharedPreferences {
        return ApplicationUtil.getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
    }

    private fun getStringByKey(key: String, defaultValue: String? = ""): String? {
        return getSp().getString(key, defaultValue)
    }

    // *** 远程/本地环境切换 ***
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

    // *** 远程/本地url修改 ***
    fun getLocalIPAddress(): String {
        return getStringByKey(LOCAL_IP_KEY, defaultValue = "0.0.0.0")!!
    }

    fun setLocalIPAddress(url: String) {
        getSp().edit().putString(LOCAL_IP_KEY, url).apply()
    }

    fun getRemoteIPAddress(): String? {
        return getStringByKey(REMOTE_IP_KEY, defaultValue = null)
    }

    fun setRemoteIPAddress(url: String) {
        getSp().edit().putString(REMOTE_IP_KEY, url).apply()
    }

    // *** 用户信息本地缓存 ***
    fun getUserTokenCache(): String {
        return getStringByKey(USER_TOKEN_KEY) ?: ""
    }

    fun setUserTokenCache(token: String) {
        getSp().edit().putString(USER_TOKEN_KEY, token).apply()
    }

    fun getUserIdCache(): String {
        return getStringByKey(USER_ID_KEY) ?: ""
    }

    fun setUserIdCache(userId: String) {
        getSp().edit().putString(USER_ID_KEY, userId).apply()
    }

}