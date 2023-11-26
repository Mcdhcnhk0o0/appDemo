package com.example.appdemo.network.service

import com.example.appdemo.network.ServiceCreator
import com.example.appdemo.network.protocol.LoginServiceApi

object LoginService {

    private val service: LoginServiceApi = ServiceCreator.create(LoginServiceApi::class.java)

    @JvmStatic
    fun getService(): LoginServiceApi {
        return service
    }

}