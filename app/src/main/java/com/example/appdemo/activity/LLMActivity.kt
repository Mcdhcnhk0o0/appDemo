package com.example.appdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.appdemo.activity.viewmodel.LLMViewModel
import com.example.appdemo.network.helper.ChatHelper
import com.example.router.annotation.Router


@Router(url = "native://llm", description = "大模型对话")

class LLMActivity: ComponentActivity() {

    private val chatHelper = ChatHelper()

    private val llmViewModel by viewModels<LLMViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}