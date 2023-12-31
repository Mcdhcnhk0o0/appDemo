package com.example.appdemo.network.helper

import com.example.appdemo.network.protocol.TrackServiceApi
import com.example.appdemo.pojo.dao.TrackEvent

class TrackHelper: AbstractApiHelper<TrackServiceApi>() {

    override fun getInnerService(): Class<out TrackServiceApi> {
        return TrackServiceApi::class.java
    }

    fun uploadTrackEvent(trackEvent: TrackEvent) {
        bindResponse(withService().uploadTrackEvent(trackEvent = trackEvent), null)
    }

    fun getRecentTrackEvents(eventType: Int, pageNum: Int, pageSize: Int, response: ApiResponse<List<TrackEvent>>){
        bindResponse(withService().getRecentTrackEvents(eventType, pageNum, pageSize), response)
    }

}