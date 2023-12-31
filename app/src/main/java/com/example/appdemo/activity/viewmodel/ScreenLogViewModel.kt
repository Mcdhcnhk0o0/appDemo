package com.example.appdemo.activity.viewmodel

import androidx.lifecycle.ViewModel

class ScreenLogViewModel: ViewModel() {

    val screenEventType = 90001

    val screenEventCode = "screen_record"

    var pageNum = 1

    var pageSize = 20

}