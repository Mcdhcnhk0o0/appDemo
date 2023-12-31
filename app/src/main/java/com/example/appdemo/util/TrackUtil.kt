package com.example.appdemo.util

import com.alibaba.fastjson.JSON
import com.example.appdemo.database.entity.ScreenState
import com.example.appdemo.pojo.dao.TrackEvent

object TrackUtil {

    private const val screenEventType = 90001
    private const val screenEventCode = "screen_record"

    @JvmStatic
    fun buildScreenTrackEvent(state: ScreenState): TrackEvent {
        val body: MutableMap<String, Any> = HashMap()
        body["status"] = state
        return TrackEvent.Companion.Builder()
            .eventType(screenEventType)
            .eventCode(screenEventCode)
            .body(body)
            .build()
    }

    @JvmStatic
    fun getScreenStateFromTrackEvent(event: TrackEvent): ScreenState {
        val bodyMap = JSON.parse(event.eventBody) as Map<*, *>
        when (bodyMap["status"]) {
            "SCREEN_ON" -> return ScreenState.SCREEN_ON
            "SCREEN_OFF" -> return ScreenState.SCREEN_OFF
            "USER_PRESENT" -> return ScreenState.USER_PRESENT
        }
        return ScreenState.UNKNOWN
    }

}