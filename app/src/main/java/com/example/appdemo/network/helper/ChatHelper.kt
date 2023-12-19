package com.example.appdemo.network.helper

import com.example.appdemo.network.protocol.ChatServiceApi
import com.example.appdemo.pojo.vo.ChatHistoryVO
import com.example.appdemo.pojo.vo.LlmVO

class ChatHelper: AbstractApiHelper<ChatServiceApi>() {

    override fun getInnerService(): Class<out ChatServiceApi> {
        return ChatServiceApi::class.java
    }

    fun startLLMConnection(llmResponse: ApiResponse<Boolean>) {
        bindResponse(withService().startLLMConnection(), llmResponse)
    }

    fun sendQueryToLLM(query: String, llmResponse: ApiResponse<LlmVO>) {
        bindResponse(withService().sendQuery(query), llmResponse)
    }

    fun getChatHistory(userId: String, pageNum: Int, pageSize: Int, llmResponse: ApiResponse<List<ChatHistoryVO>>) {
        bindResponse(withService().getChatHistory(userId, pageNum, pageSize), llmResponse)
    }

}