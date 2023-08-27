package com.example.appdemo.broadcast.listener

object ListenerProxy {

    private val screenStatusListeners = mutableListOf<ScreenStatusListener>()

    fun addScreenStatusListener(listener: ScreenStatusListener) {
        screenStatusListeners.add(listener)
    }

    fun removeScreenStatusListener(listener: ScreenStatusListener) {
        screenStatusListeners.remove(listener)
    }

    fun getScreenStatusListeners(): MutableList<ScreenStatusListener> {
        return screenStatusListeners
    }

}