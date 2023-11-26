package com.example.appdemo.service

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import java.lang.ref.WeakReference

object ActivityManagerService: Application.ActivityLifecycleCallbacks {

    private var isBackground = false

    private var topActivity: WeakReference<Activity>? = null

    private var activityCount = 0

    fun init() { }

    @JvmStatic
    fun getTopActivity(): WeakReference<Activity>? {
        Log.d("ActivityManagerService", "current top activity: ${topActivity?.get().toString()}")

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
            Log.d("ActivityManagerService", "app onForeground")
        }
        activityCount++
    }

    override fun onActivityResumed(activity: Activity) {
        topActivity = WeakReference(activity)
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