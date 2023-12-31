package com.example.appdemo.service;

import static androidx.core.app.NotificationCompat.PRIORITY_MIN;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.room.Room;

import com.example.appdemo.R;
import com.example.appdemo.broadcast.ScreenStatusReceiver;
import com.example.appdemo.broadcast.listener.ListenerProxy;
import com.example.appdemo.broadcast.listener.ScreenStatusListener;
import com.example.appdemo.database.ScreenStatusRecordDatabase;
import com.example.appdemo.database.dao.ScreenStatusDao;
import com.example.appdemo.database.entity.ScreenState;
import com.example.appdemo.database.entity.ScreenStatusBean;
import com.example.appdemo.network.helper.TrackHelper;
import com.example.appdemo.pojo.dao.TrackEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ScreenStatusRecordService extends Service implements ScreenStatusListener {

    private static final String TAG = ScreenStatusRecordService.class.getSimpleName();

    private final TrackHelper trackHelper = new TrackHelper();

    private ScreenStatusRecordDatabase recordDatabase;

    private static final int ONE_MINUTE = 6 * 1000;
    private static final int PENDING_REQUEST = 0;

    public ScreenStatusRecordService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        String channelId = "screen_monitor";
        createNotificationChannel(channelId, "屏幕监控");
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId);
        Notification notification =
                builder
                .setContentText("屏幕监控服务已启动！")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(PRIORITY_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(1, notification);
        initDatabaseAsync();
        ListenerProxy.INSTANCE.addScreenStatusListener(this);
        Log.d(TAG, "service created!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerAtTime = SystemClock.elapsedRealtime() + ONE_MINUTE;
        Intent i = new Intent(this, ScreenStatusReceiver.class);
        @SuppressLint("WrongConstant")
        PendingIntent pIntent = PendingIntent.getBroadcast(this, PENDING_REQUEST, i, PENDING_REQUEST | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pIntent);
        Log.d(TAG, "prepare to send broadcast when: " + triggerAtTime + "(as well as " + new Date(triggerAtTime));
        return super.onStartCommand(intent, flags, startId);
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
        sendTrackEvent(record);
        Log.d(TAG, "screen is On");
        butIWantToReborn();
    }

    @Override
    public void onScreenOff() {
        ScreenStatusBean record = generateRecord(ScreenState.SCREEN_OFF);
        appendRecord(record);
        sendTrackEvent(record);
        Log.d(TAG, "screen is Off");
    }

    @Override
    public void onUserPresent() {
        ScreenStatusBean record = generateRecord(ScreenState.USER_PRESENT);
        appendRecord(record);
        sendTrackEvent(record);
        Log.d(TAG, "user unlock the screen");
    }

    private void butIWantToReborn() {
        try {
//            Intent intent = new Intent("com.example.appdemo.screenStatusRecordService");
//            sendBroadcast(intent);
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

    private void sendTrackEvent(ScreenStatusBean record) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", record.state);
        TrackEvent event = new TrackEvent.Companion.Builder()
                .eventType(90001)
                .eventCode("screen_record")
                .body(body)
                .build();
        trackHelper.uploadTrackEvent(event);
    }

    private String createNotificationChannel(String channelId, String channelName) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) {
            return channelId;
        }
        NotificationChannel chan = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager service =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        service.createNotificationChannel(chan);
        return channelId;
    }

}