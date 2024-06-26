package com.example.appdemo.service

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.example.router.annotation.Service
import com.example.router.annotation.ServiceMethod
import java.lang.ref.WeakReference

@Service(name = "activity")
object ActivityManagerService: Application.ActivityLifecycleCallbacks {

    private var TAG = ActivityManagerService::class.java.simpleName

    private var isBackground = false

    private var topActivity: WeakReference<Activity>? = null

    private var activityCount = 0

    fun init() { }

    @JvmStatic
    @ServiceMethod
    fun getTopActivity(): WeakReference<Activity>? {
        Log.d(TAG, "current top activity: ${topActivity?.get().toString()}")

        return topActivity
    }

    fun isAppBackground(): Boolean {
        return isBackground
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        topActivity = WeakReference(activity)
    }

    override fun onActivityStarted(activity: Activity) {
        if (activityCount == 0) {
            isBackground = false
            Log.d(TAG, "app onForeground")
        }
        activityCount++
    }

    override fun onActivityResumed(activity: Activity) {
        topActivity = WeakReference(activity)
        Log.d(TAG, "$activity is resumed")
    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
        activityCount--
        if (activityCount == 0) {
            isBackground = true
            Log.d("ActivityManagerService", "app onBackground")
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {

    }
}