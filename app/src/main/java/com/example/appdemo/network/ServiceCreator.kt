package com.example.appdemo.network

import android.util.Log
import com.example.appdemo.BuildConfig
import com.example.appdemo.util.SharedPrefUtil
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceCreator {

    private const val TAG = "HttpDebugLogger"

    private const val defaultRemoteIP = "8.141.90.135"

    private var retrofit: Retrofit? = null

    private var cachedHttpClient: OkHttpClient =
        buildHttpClientWithToken(SharedPrefUtil.getUserTokenCache())

    @JvmStatic
    @Synchronized
    fun <T> create(serviceClass: Class<T>): T {
        if (retrofit == null) {
            retrofit = getRetrofit(baseUrl(), cachedHttpClient)
        }
        return retrofit!!.create(serviceClass)
    }

    @JvmStatic
    fun refreshToken(token: String) {
        cachedHttpClient = buildHttpClientWithToken(token)
        Log.d(TAG, "token is refreshed!")
        Log.d(TAG, "current token: $token")
        Log.d(TAG, "current userId: ${SharedPrefUtil.getUserIdCache()}")
        Log.d(TAG, "current request mode: ${SharedPrefUtil.getBaseUrlSetting()}")
        retrofit = getRetrofit(baseUrl(), cachedHttpClient)
    }

    @JvmStatic
    fun refreshLocalIP(ip: String, needApply: Boolean) {
        SharedPrefUtil.setLocalIPAddress(ip)
        if (needApply) {
            retrofit = getRetrofit(urlFormat(ip), cachedHttpClient)
        }
    }

    @JvmStatic
    fun refreshRemoteIP(ip: String, needApply: Boolean) {
        SharedPrefUtil.setRemoteIPAddress(ip)
        if (needApply) {
            retrofit = getRetrofit(urlFormat(ip), cachedHttpClient)
        }
    }

    @JvmStatic
    private fun buildHttpClientWithToken(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor { message ->
                            Log.d(TAG, message)
                        }.apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        }
                    )
                }
            }
            .apply {
                addInterceptor { chain ->
                    val original = chain.request()
                    return@addInterceptor chain.proceed(
                        chain.request().newBuilder()
                            .header("token", token)
                            .method(original.method(), original.body())
                            .build()
                    )
                }
            }
            .build()
    }

    private fun baseUrl(): String {
        val localIP = SharedPrefUtil.getLocalIPAddress()
        val remoteIP = SharedPrefUtil.getRemoteIPAddress() ?: defaultRemoteIP
        val currentIP = when (SharedPrefUtil.getBaseUrlSetting()) {
            "debug" -> localIP
            "release" -> remoteIP
            else -> remoteIP
        }
        if (SharedPrefUtil.getRemoteIPAddress() == null) {
            SharedPrefUtil.setRemoteIPAddress(defaultRemoteIP)
        }
        return urlFormat(currentIP)
    }

    private fun getRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @JvmStatic
    private fun urlFormat(ip: String): String {
        return "http://$ip:8880/demo/"
    }

}