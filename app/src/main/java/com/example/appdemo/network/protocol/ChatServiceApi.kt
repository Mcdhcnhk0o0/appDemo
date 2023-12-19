package com.example.appdemo.network.protocol

import com.example.appdemo.network.response.ApiResult
import com.example.appdemo.pojo.vo.ChatHistoryVO
import com.example.appdemo.pojo.vo.LlmVO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatServiceApi {

    @GET("llm/start")
    fun startLLMConnection(): Call<ApiResult<Boolean>>

    @GET("llm/send")
    fun sendQuery(
        @Query("message") message: String
    ): Call<ApiResult<LlmVO>>

    @GET("chat/record/get")
    fun getChatHistory(
        @Query("userId") userId: String,
        @Query("pageNum") pageNum: Int,
        @Query("pageSize") pageSize: Int
    ): Call<ApiResult<ChatHistoryVO>>

}