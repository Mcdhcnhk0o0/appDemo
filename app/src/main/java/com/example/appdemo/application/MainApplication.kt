package com.example.appdemo.application

import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.example.appdemo.broadcast.ScreenStatusReceiver
import com.example.appdemo.flutter.FlutterRuntimeUtil
import com.example.appdemo.router.OneRouter
import com.example.appdemo.router.wrapper.FlutterRouterWrapper
import com.example.appdemo.service.ActivityManagerService
import com.example.appdemo.service.ScreenStatusRecordService
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoost.Callback
import io.flutter.embedding.engine.FlutterEngine

class MainApplication: BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        initFlutter()
        initRouter()
        initListener()
        initService()
    }

    private fun initFlutter() {
//        FlutterRuntimeUtil.initFlutterEngineWithContext(this)
        FlutterBoost.instance().setup(
            this,
            FlutterRouterWrapper.getInstance()
        ) {
            Log.d("FlutterEngine", "flutter engine $it is initialized")
        }
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