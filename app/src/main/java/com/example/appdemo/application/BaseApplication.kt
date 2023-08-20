package com.example.appdemo.application

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import java.lang.reflect.Method

open class BaseApplication: Application() {

    private val moduleApplications = ArrayList<Application>()

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        initModuleApplications()
    }

    override fun onCreate() {
        super.onCreate()
        moduleApplications.forEach { it.onCreate() }
    }

    private fun initModuleApplications() {
        val info = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        if (info.metaData == null)
            return
        val apps = info.metaData.keySet()
        apps.forEach {
            try {
                var cla = Class.forName(it.toString())
                var app = cla.newInstance()
                if (app is Application && cla.name != this::class.java.name) {
                    initModuleApplications(app)
                }

            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    }

    private fun initModuleApplications(app: Application) {
        val method: Method? =
            Application::class.java.getDeclaredMethod("attach", Context::class.java)
        if (method != null) {
            method.isAccessible = true
            method.invoke(app, baseContext)
            moduleApplications.add(app)
        }
    }

}