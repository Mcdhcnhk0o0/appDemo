package com.example.appdemo.network.protocol

import com.example.appdemo.network.response.ApiResult
import com.example.appdemo.pojo.dao.TrackEvent
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TrackServiceApi {

    @POST("track/upload")
    fun uploadTrackEvent(
        @Body trackEvent: TrackEvent
    ): Call<ApiResult<Boolean>>

    @GET("track/get")
    fun getRecentTrackEvents(
        @Query("eventType") eventType: Int,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<ApiResult<List<TrackEvent>>>

}