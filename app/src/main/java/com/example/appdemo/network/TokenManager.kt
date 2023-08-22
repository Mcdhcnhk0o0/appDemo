package com.example.appdemo.network

object TokenManager {

    const val TOKEN = "token"

    private var token: String = ""

    fun setToken(newToken: String) {
        token = newToken
    }

    fun getToken(): String {
        return token
    }

}