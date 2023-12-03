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

    private const val remoteBaseUrl = "http://123.249.16.84:8880/demo/"

    private const val defaultLocalBaseUrl = "http://192.168.0.102:8880/demo/"

    private var localBaseUrl = defaultLocalBaseUrl

    private var retrofit: Retrofit? = null

    private var cachedHttpClient: OkHttpClient =
        buildHttpClientWithToken(SharedPrefUtil.getUserTokenCache())

    @JvmStatic
    @Synchronized
    fun <T> create(serviceClass: Class<T>): T {
        if (retrofit == null) {
            retrofit = getRetrofit(getBaseUrl(), cachedHttpClient)
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
        retrofit = getRetrofit(getBaseUrl(), cachedHttpClient)
    }

    @JvmStatic
    fun refreshDebugUrl(url: String) {
        val newDebugUrl = "http://${url}:8880/demo/"
        SharedPrefUtil.setDebugUrlAddress(newDebugUrl)
        localBaseUrl = newDebugUrl
        retrofit = getRetrofit(newDebugUrl, cachedHttpClient)
    }

    @JvmStatic
    private fun buildHttpClientWithToken(token: String): OkHttpClient {
        return OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    addInterceptor(
                        HttpLoggingInterceptor {
                                message -> Log.d(TAG, message)
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

    private fun getDebugUrl(): String {
        if (SharedPrefUtil.getDebugUrlAddress().isBlank()) {
            return defaultLocalBaseUrl
        }
        return SharedPrefUtil.getDebugUrlAddress()
    }

    private fun getBaseUrl(): String {
        val baseUrl = when (SharedPrefUtil.getBaseUrlSetting()) {
            "debug" -> localBaseUrl
            "release" -> remoteBaseUrl
            else -> remoteBaseUrl
        }
        return baseUrl
    }

    private fun getRetrofit(baseUrl: String, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}