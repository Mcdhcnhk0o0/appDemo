package com.example.appdemo.util

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.Window
import androidx.compose.ui.graphics.Color
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.example.appdemo.service.ActivityManagerService
import java.lang.ref.WeakReference
import kotlin.math.roundToInt

object StatusBarUtil: OnApplyWindowInsetsListener {

    private var currentActivity: WeakReference<Activity>? = null
    private var cachedStatusBarHeight = 0

    fun wantToGetStatusBarHeight(activity: Activity) {
        currentActivity = WeakReference(activity)
        ViewCompat.setOnApplyWindowInsetsListener(activity.window.decorView, StatusBarUtil)
    }

    fun getStatusBarHeight(): Int {
        if (currentActivity == null || currentActivity!!.get() == null) {
            throw IllegalArgumentException("Please call wantToGetStatusBarHeight when activity onCreate!")
        }
        Log.d("StatusBarUtil", "currentStatusBarHeight: $cachedStatusBarHeight")
        return px2Dp(cachedStatusBarHeight)
    }

    fun allowLayoutBehindStatusBar() {
        val currentWindow = acquireWindowFromManager()
        currentWindow?.let {
            WindowCompat.setDecorFitsSystemWindows(currentWindow, false)
            currentWindow.statusBarColor = Color.Transparent.value.toInt()
        }
    }

    fun changeStatusBarLightMode() {
        val currentWindow = acquireWindowFromManager()
        currentWindow?.let {
            WindowCompat.getInsetsController(currentWindow, currentWindow.decorView).isAppearanceLightStatusBars = false
        }
    }

    fun changeStatusBarDarkMode() {
        val currentWindow = acquireWindowFromManager()
        currentWindow?.let {
            WindowCompat.getInsetsController(currentWindow, currentWindow.decorView).isAppearanceLightStatusBars = true
        }
    }

    private fun px2Dp(px: Int): Int {
        val currentActivity = ActivityManagerService.getTopActivity()
        val density = currentActivity?.get()?.resources?.displayMetrics?.density ?: 2.0f
        return (px / density).roundToInt()
    }

    private fun acquireWindowFromManager():Window? {
        val currentActivity = ActivityManagerService.getTopActivity()
        if (currentActivity != null && currentActivity.get() is Activity) {
            return currentActivity.get()!!.window
        }
        return null
    }

    override fun onApplyWindowInsets(v: View, insets: WindowInsetsCompat): WindowInsetsCompat {
        val systemWindow = insets.getInsets(WindowInsetsCompat.Type.systemBars() or
                WindowInsetsCompat.Type.displayCutout())
        cachedStatusBarHeight = systemWindow.top
        Log.d("StatusBarUtil", "currentStatusBarHeightInListener: $cachedStatusBarHeight")
        return insets
    }

}