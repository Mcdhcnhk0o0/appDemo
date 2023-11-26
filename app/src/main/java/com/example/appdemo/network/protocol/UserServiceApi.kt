package com.example.appdemo.network.protocol

import com.example.appdemo.pojo.dao.UserDetail
import com.example.appdemo.pojo.vo.UserInfoVO
import com.example.appdemo.network.response.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserServiceApi {

    @GET("user/get")
    fun getUserInformation(
        @Query("userId") userId: String
    ):Call<Result<UserInfoVO>>

    @GET("user/get/detail")
    fun getUserDetail(
        @Query("userId") userId: String
    ): Call<Result<UserDetail>>

}