package com.example.appdemo.router.wrapper;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appdemo.flutter.FlutterRootActivity;
import com.example.appdemo.router.OneRouter;
import com.example.appdemo.router.protocol.IRouter;
import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.FlutterBoostDelegate;
import com.idlefish.flutterboost.FlutterBoostRouteOptions;
import com.idlefish.flutterboost.containers.FlutterBoostActivity;

import java.util.HashMap;
import java.util.Map;

import io.flutter.embedding.android.FlutterActivityLaunchConfigs;

public class FlutterRouterWrapper implements IRouter, FlutterBoostDelegate {

    public static FlutterRouterWrapper getInstance() {
        return InnerClass.instance;
    }

    @Override
    public boolean dispatch(Context context, @NonNull String url, @Nullable Map<String, String> params) {
        if (!url.startsWith("flutter")) {
            return false;
        }
        Map<String, Object> arguments = new HashMap<>();
        if (params != null) {
            arguments.putAll(params);
        }
        FlutterBoostRouteOptions routeOptions = new FlutterBoostRouteOptions.Builder()
                .pageName(url)
                .arguments(arguments)
                .build();
        pushFlutterRoute(routeOptions);
        return true;
    }

    @Override
    public void pushNativeRoute(FlutterBoostRouteOptions options) {
        OneRouter.getInstance().dispatch(options.pageName());
    }

    @Override
    public void pushFlutterRoute(FlutterBoostRouteOptions options) {
        Intent intent = new FlutterBoostActivity.CachedEngineIntentBuilder(FlutterRootActivity.class)
                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                .destroyEngineWithActivity(false)
                .uniqueId(options.uniqueId())
                .url(options.pageName())
                .urlParams(options.arguments())
                .build(FlutterBoost.instance().currentActivity());
        FlutterBoost.instance().currentActivity().startActivity(intent);
    }

    private static final class InnerClass {
        private static final FlutterRouterWrapper instance = new FlutterRouterWrapper();
    }

}
