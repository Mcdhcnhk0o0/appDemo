package com.example.appdemo.util

import com.example.appdemo.router.OneRouter

object RouterUtil {

    private const val mainPageUrl: String = "native://main"

    fun gotoMainPage() {
        OneRouter.getInstance().dispatch(mainPageUrl)
    }

}