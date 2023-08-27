package com.example.appdemo.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import androidx.room.Room;

import com.example.appdemo.broadcast.ScreenStatusBroadcastReceiver;
import com.example.appdemo.broadcast.listener.ListenerProxy;
import com.example.appdemo.broadcast.listener.ScreenStatusListener;
import com.example.appdemo.database.ScreenStatusRecordDatabase;
import com.example.appdemo.database.dao.ScreenStatusDao;
import com.example.appdemo.database.entity.ScreenState;
import com.example.appdemo.database.entity.ScreenStatusBean;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScreenStatusRecordService extends Service implements ScreenStatusListener {

    private static final String TAG = ScreenStatusRecordService.class.getSimpleName();

    private ScreenStatusRecordDatabase recordDatabase;

    private final String recordSp = "ScreenStatusRecordSp";
    private final String recordKey = "ScreenStatusRecordKey";

    public ScreenStatusRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabaseAsync();
        ListenerProxy.INSTANCE.addScreenStatusListener(this);
        Log.d(TAG, "service created!");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerProxy.INSTANCE.removeScreenStatusListener(this);
        Log.d(TAG, "service is destroyed!");
        butIWantToReborn();
    }

    @Override
    public void onScreenOn() {
        ScreenStatusBean record = generateRecord(ScreenState.SCREEN_ON);
        appendRecord(record);
        Log.d(TAG, "screen is On");
        butIWantToReborn();
    }

    @Override
    public void onScreenOff() {
        ScreenStatusBean record = generateRecord(ScreenState.SCREEN_OFF);
        appendRecord(record);
        Log.d(TAG, "screen is Off");
    }

    @Override
    public void onUserPresent() {
        ScreenStatusBean record = generateRecord(ScreenState.USER_PRESENT);
        appendRecord(record);
        Log.d(TAG, "user unlock the screen");
    }

    private void butIWantToReborn() {
        try {
            Intent intent = new Intent("com.example.appdemo.screenStatusRecordService");
            sendBroadcast(intent);
            Log.d(TAG, "try to restart!");
        } catch (Exception e) {
            Log.e(TAG, "restart error: " + e.getMessage());
        }

    }

    private void initDatabaseAsync() {
        new Thread(
            () -> {
                if (recordDatabase == null) {
                    recordDatabase = Room.databaseBuilder(this, ScreenStatusRecordDatabase.class, "screen_db").build();
                }
            }
        ).start();
    }

    private ScreenStatusBean generateRecord(ScreenState state) {
        ScreenStatusBean statusBean = new ScreenStatusBean();
        statusBean.state = state;
        statusBean.timeStamp = System.currentTimeMillis();
        return statusBean;
    }

    private void appendRecord(ScreenStatusBean record) {
        if (recordDatabase != null) {
            new Thread(()-> {
                ScreenStatusDao statusDao = recordDatabase.screenStatusDao();
                statusDao.addRecord(record);
            }).start();
            Log.d(TAG, "insert record into db successful");
        } else {
            Log.e(TAG, "database screen_db is *not* initialized!");
        }
    }

}