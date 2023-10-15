package com.example.appdemo.util

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object PermissionUtil {

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(context: Context, permission: String, requestCode: Int) {
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                listOf(permission).toTypedArray(), requestCode)
        } else {
            throw IllegalArgumentException("context must be an instance of Activity")
        }
    }

    fun requestPermissions(context: Context, permissions: List<String>, requestCode: Int) {
        if (context is Activity) {
            ActivityCompat.requestPermissions(
                context,
                permissions.toTypedArray(), requestCode)
        } else {
            throw IllegalArgumentException("context must be an instance of Activity")
        }
    }

    fun shouldRequestPermission(context: Context, permission: String): Boolean {
        if (context is Activity) {
            return ActivityCompat.shouldShowRequestPermissionRationale(context, permission)
        }  else {
            throw IllegalArgumentException("context must be an instance of Activity")
        }
    }

    fun requestPermissionsIfNeeded(context: Context, permissions: List<String>, requestCode: Int) {

    }

}