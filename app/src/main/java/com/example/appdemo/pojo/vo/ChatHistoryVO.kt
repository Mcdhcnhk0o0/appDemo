package com.example.appdemo.pojo.vo

data class ChatHistoryVO (

    var message: String?,
    var gmtCreate: String?

) {
    constructor(): this(null, null)
}