package com.example.appdemo.application

import android.app.Application
import com.example.appdemo.router.OneRouter

class MainApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initRouter()
    }

    private fun initRouter() {
        OneRouter.getInstance().init()
        OneRouter.getInstance().registerNativeRouterModules("main")
    }

}