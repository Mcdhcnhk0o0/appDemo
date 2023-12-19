package com.example.appdemo.activity.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel


data class LLMItemModel(
    val content: String,
    val gmtCreated: String,
    val sendFromUser: Boolean
)


class LLMViewModel: ViewModel() {

    var pageNum = 1

    val pageSize = 10

    var waitingForResponse by mutableStateOf(false)

    var connectionEstablished by mutableStateOf(false)

    var currentMessage by mutableStateOf("")

    var currentChatList = mutableStateListOf<LLMItemModel>()

}