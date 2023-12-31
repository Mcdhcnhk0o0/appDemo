package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationChannelCompat;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import com.example.appdemo.R;
import com.example.appdemo.util.NotificationUtil;
import com.example.router.annotation.Router;

@Router(url = "native://notification", description = "通知测试")
public class NotificationActivity extends AppCompatActivity {

    private Button openNotification;
    private Button sendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        openNotification = findViewById(R.id.open_notification);
        sendNotification = findViewById(R.id.send_notification);
        initListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initListener() {
        openNotification.setOnClickListener(v -> {
            if (NotificationUtil.isNotificationEnable()) {
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 999);
            } else {
                NotificationUtil.startNotificationSetting();
            }
        });
        sendNotification.setOnClickListener(v -> {
            NotificationChannelCompat channel = NotificationUtil.createChannel("common_channel", "通用通知");
            NotificationUtil.sendTextNotification(channel, "通知测试", "这是一条通知的内容");
        });
    }

}