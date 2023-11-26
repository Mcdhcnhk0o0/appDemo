package com.example.appdemo.network.protocol

import com.example.appdemo.network.response.Result
import com.example.appdemo.pojo.vo.LoginVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface LoginServiceApi {

    @GET("login/loginByEmail")
    fun loginByEmail(
        @Query("email") email: String,
        @Query("password") password: String
    ): Call<Result<LoginVO>>

    @GET("login/signup")
    fun signUp(
        @Query("email") email: String,
        @Query("name") name: String,
        @Query("password") password: String
    ): Call<Result<LoginVO>>

    @GET("login/logout")
    fun logout(
        @Query("userId") userId: String
    ): Call<Result<LoginVO>>

}