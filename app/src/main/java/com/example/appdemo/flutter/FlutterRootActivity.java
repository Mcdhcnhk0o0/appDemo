package com.example.appdemo.flutter;

import io.flutter.embedding.android.FlutterActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.appdemo.R;
import com.example.appdemo.util.StatusBarUtil;
import com.example.router.annotation.Router;
import com.idlefish.flutterboost.containers.FlutterBoostActivity;

import io.flutter.embedding.engine.FlutterEngine;


@Router(url = "native://flutter/main", description = "Flutter")
public class FlutterRootActivity extends FlutterBoostActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.INSTANCE.allowLayoutBehindStatusBar();
        StatusBarUtil.INSTANCE.transparentStatusBar(getWindow());
        StatusBarUtil.INSTANCE.changeStatusBarDarkMode();
        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
    }

}

