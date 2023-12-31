package com.example.appdemo.util

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.appdemo.R

object NotificationUtil {

    private val TAG = NotificationUtil.javaClass.simpleName

    @JvmStatic
    fun isNotificationEnable(): Boolean {
        val notificationManager = NotificationManagerCompat.from(ApplicationUtil.getApplicationContext())
        return notificationManager.areNotificationsEnabled()
    }

    @JvmStatic
    fun startNotificationSetting() {
        val intent = Intent()
        val context = ApplicationUtil.getCurrentActivity() ?: ApplicationUtil.getApplicationContext()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        } else {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", context.packageName)
            intent.putExtra("app_uid", context.applicationInfo.uid)
        }
        context.startActivity(intent)
    }

    @JvmStatic
    fun createChannel(id: String, name: String): NotificationChannelCompat? {
        if (Build.VERSION_CODES.O > Build.VERSION.SDK_INT) {
            return null
        }
        val notificationManager = NotificationManagerCompat.from(ApplicationUtil.getApplicationContext())
        // the field "name" must not be null or an IllegalArgumentException will be thrown
        val channel = NotificationChannelCompat.Builder(id, NotificationManager.IMPORTANCE_DEFAULT)
            .setName(name)
            .build()
        notificationManager.createNotificationChannel(channel)
        return channel
    }

    @JvmStatic
    @SuppressLint("MissingPermission")
    fun sendTextNotification(channel: NotificationChannelCompat?, title: String, content: String) {
        val channelId = channel?.id ?: "channelId"
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(ApplicationUtil.getApplicationContext(), channelId)
        builder.setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.app_icon)
        val notificationManager = NotificationManagerCompat.from(ApplicationUtil.getApplicationContext())
        if (notificationManager.areNotificationsEnabled()) {
            notificationManager.notify(1, builder.build())
        }
    }

}