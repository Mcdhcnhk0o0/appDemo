package com.example.appdemo.network

import android.util.Log
import com.example.appdemo.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ServiceCreator {

    private const val TAG = "HttpDebugLogger"

    private const val localBaseUrl = "http://192.168.0.103:8880/demo/"

    private const val remoteBaseUrl = "http://123.249.16.84:8880/demo/"

    private val okHttpClient: OkHttpClient =
        OkHttpClient.Builder()
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
                            .header(TokenManager.TOKEN, TokenManager.getToken())
                            .method(original.method(), original.body())
                            .build()
                    )
                }
            }
            .build()

    private val retrofit: Retrofit =
        Retrofit.Builder()
            .baseUrl(if(BuildConfig.DEBUG) localBaseUrl else remoteBaseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }

}