package com.example.appdemo.flutter;

import io.flutter.embedding.android.FlutterActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.router.annotation.Router;

import io.flutter.embedding.engine.FlutterEngine;


@Router(url = "native://flutter/main", description = "Flutter")
public class FlutterRootActivity extends FlutterActivity {

    public static CachedEngineIntentBuilder withCachedEngine(@NonNull String cachedEngineId) {
        return new CachedEngineIntentBuilder(FlutterRootActivity.class, cachedEngineId);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
    }

}

