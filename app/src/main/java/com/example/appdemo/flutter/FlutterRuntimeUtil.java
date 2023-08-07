package com.example.appdemo.flutter;

import android.content.Context;
import android.content.Intent;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

public class FlutterRuntimeUtil {

    public static final String DEFAULT_FLUTTER_ENGINE = "cachedEngine";

    public static void initFlutterEngineWithContext(Context context) {
        FlutterEngine flutterEngine = new FlutterEngine(context);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );
        FlutterEngineCache.getInstance().put(FlutterRuntimeUtil.DEFAULT_FLUTTER_ENGINE, flutterEngine);
    }

    public static void openFlutterContainerWithCachedEngine(Context context, String url) {
        Intent intent = FlutterRootActivity.withCachedEngine(DEFAULT_FLUTTER_ENGINE).build(context);
        context.startActivity(intent);
    }

}
