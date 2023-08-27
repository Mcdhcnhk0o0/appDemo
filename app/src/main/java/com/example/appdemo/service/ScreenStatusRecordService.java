package com.example.appdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ScreenStatusRecordService extends Service {
    public ScreenStatusRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}