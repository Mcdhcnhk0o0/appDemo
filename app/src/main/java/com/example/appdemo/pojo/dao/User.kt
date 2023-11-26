package com.example.appdemo.pojo.dao

data class User(
    var userId: String?,
    var email: String?,
    var userName: String?,
    var userNickname: String?,
    var password: String?,
    var extraInfo: String?
) {
    constructor() :
            this(null, null, null, null, null, null)
}