package com.example.appdemo.application

import android.content.Intent
import android.content.IntentFilter
import com.example.appdemo.broadcast.ScreenStatusReceiver
import com.example.appdemo.flutter.FlutterRuntimeUtil
import com.example.appdemo.router.OneRouter
import com.example.appdemo.service.ActivityManagerService
import com.example.appdemo.service.ScreenStatusRecordService

class MainApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initFlutter()
        initRouter()
        initListener()
        initService()
    }

    private fun initFlutter() {
        FlutterRuntimeUtil.initFlutterEngineWithContext(this)
    }

    private fun initRouter() {
        OneRouter.getInstance().init()
        OneRouter.getInstance().registerNativeRouterModules("main")
        OneRouter.getInstance().registerNativeRouterModules("home")
    }

    private fun initListener() {
        val intent = Intent(this, ScreenStatusRecordService::class.java)
        startService(intent)

        val filter = IntentFilter()
        filter.addAction(Intent.ACTION_SCREEN_ON)
        filter.addAction(Intent.ACTION_SCREEN_OFF)
        filter.addAction(Intent.ACTION_USER_PRESENT)
        registerReceiver(ScreenStatusReceiver(), filter)
    }

    private fun initService() {
        registerActivityLifecycleCallbacks(ActivityManagerService)
    }

}