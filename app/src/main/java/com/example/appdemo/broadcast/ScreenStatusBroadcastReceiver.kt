package com.example.appdemo.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.appdemo.broadcast.listener.ListenerProxy
import com.example.appdemo.broadcast.listener.ScreenStatusListener
import com.example.appdemo.service.ScreenStatusRecordService

class ScreenStatusBroadcastReceiver : BroadcastReceiver() {

    private val screenStatusListenerProxy = ListenerProxy

    override fun onReceive(context: Context, intent: Intent) {
        tryToStartScreenService(context)
        val action = intent.action
        val listeners = screenStatusListenerProxy.getScreenStatusListeners()

        if (Intent.ACTION_SCREEN_ON == action) {
            listeners.forEach { it.onScreenOn() }
        } else if (Intent.ACTION_SCREEN_OFF == action) {
            listeners.forEach { it.onScreenOff() }
        } else if (Intent.ACTION_USER_PRESENT == action) {
            listeners.forEach { it.onUserPresent() }
        }
    }

    private fun tryToStartScreenService(context: Context) {
        val intent = Intent(context, ScreenStatusRecordService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }

    }


}