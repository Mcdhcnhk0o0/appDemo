package com.example.appdemo.network.service

import com.example.appdemo.network.ServiceCreator

object LoginService {

    private val service: LoginServiceApi = ServiceCreator.create(LoginServiceApi::class.java)

    fun getService(): LoginServiceApi {
        return service
    }

}