package com.example.appdemo.pojo.dao

data class UserDetail (
    var id: Int?,
    var userId: String?,
    var gender: String?,
    var birthday: String?,
    var avatar: String?,
    var address: String?,
    var introduction: String?,
    var extendInfo: String?
) {
    constructor():
            this(null, null, null, null, null, null, null, null)

}