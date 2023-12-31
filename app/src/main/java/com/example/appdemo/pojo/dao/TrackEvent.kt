package com.example.appdemo.pojo.dao

import com.alibaba.fastjson.JSON
import com.example.appdemo.util.DeviceInfoUtil
import com.example.appdemo.util.UserInfoUtil

data class TrackEvent (
    val userId: String? = null,
    val deviceId: String? = null,
    val eventType: Int? = null,
    val eventCode: String? = null,
    val eventBody: String? = null,
    val localTimestamp: String? = null
) {
    constructor():
            this(null, null, null, null, null, null)

    companion object {

        class Builder {

            val userId = UserInfoUtil.getUserId()
            private val deviceId = DeviceInfoUtil.getAndroidId()
            private val localTimestamp = System.currentTimeMillis().toString()

            private var eventType: Int? = null
            private var eventCode: String? = null
            private var eventBody: String? = null

            fun eventType(type: Int): Builder {
                this.eventType = type
                return this
            }

            fun eventCode(code: String): Builder {
                this.eventCode = code
                return this
            }

            fun body(body: Map<String, Any>): Builder {
                this.eventBody = JSON.toJSONString(body)
                return this
            }

            fun build(): TrackEvent {
                return TrackEvent(userId, deviceId, eventType, eventCode, eventBody, localTimestamp)
            }

        }
    }
}