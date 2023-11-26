package com.example.appdemo.util

import android.app.Application
import android.content.Context

object ApplicationUtil {

    private var application: Application? = null

    fun init(application: Application) {
        this.application = application
    }

    fun getApplicationContext(): Application {
        if (application == null) {
            throw IllegalStateException()
        }
        return application!!
    }

}