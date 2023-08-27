package com.example.appdemo.network.protocol

import com.example.appdemo.network.response.Result
import com.example.appdemo.network.response.LoginResult
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface LoginServiceApi {

    @GET("login/loginByEmail")
    fun loginByEmail(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<Result<LoginResult>>

    @GET("login/logout")
    fun logout(
        @Query("userId") userId: String
    ): Call<JsonObject>

}