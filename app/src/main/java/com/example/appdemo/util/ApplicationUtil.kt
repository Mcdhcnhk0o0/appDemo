package com.example.appdemo.util

import android.app.Activity
import android.app.Application
import android.content.Context
import com.example.appdemo.service.ActivityManagerService
import com.example.router.annotation.Service
import com.example.router.annotation.ServiceMethod


@Service(name = "application")
class ApplicationUtil {

    companion object {

        private var application: Application? = null

        @JvmStatic
        fun init(application: Application) {
            this.application = application
        }

        @JvmStatic
        @ServiceMethod
        fun getApplicationContext(): Application {
            if (application == null) {
                throw IllegalStateException()
            }
            return application!!
        }

        @JvmStatic
        @ServiceMethod
        fun getCurrentActivity(): Activity? {
            val activityRef = ActivityManagerService.getTopActivity()
            if (activityRef != null) {
                return activityRef.get()
            }
            return null
        }

    }


}