package com.example.appdemo.network.service

import com.example.appdemo.network.ServiceCreator
import com.example.appdemo.network.protocol.UserServiceApi

object UserService {

    private val service: UserServiceApi = ServiceCreator.create(UserServiceApi::class.java)

    @JvmStatic
    fun getService(): UserServiceApi {
        return service
    }

}