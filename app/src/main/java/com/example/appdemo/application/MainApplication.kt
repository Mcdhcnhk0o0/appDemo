package com.example.appdemo.application

import android.content.Intent
import android.content.IntentFilter
import com.example.appdemo.broadcast.ScreenStatusBroadcastReceiver
import com.example.appdemo.router.OneRouter
import com.example.appdemo.service.ScreenStatusRecordService

class MainApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initRouter()
        initListener()
    }

    private fun initRouter() {
        OneRouter.getInstance().init()
        OneRouter.getInstance().registerNativeRouterModules("main")
    }

    private fun initListener() {
        val intent = Intent(this, ScreenStatusRecordService::class.java)
        startService(intent)

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        registerReceiver(ScreenStatusBroadcastReceiver(), filter)
    }

}