package com.example.appdemo.util

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.appdemo.service.ActivityManagerService

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

    fun getCurrentActivity(): Activity? {
        val activityRef = ActivityManagerService.getTopActivity()
        if (activityRef != null) {
            return activityRef.get()
        }
        return null
    }

}