package com.example.appdemo.router.wrapper;

import android.content.Context;

import com.example.appdemo.flutter.FlutterRuntimeUtil;
import com.example.appdemo.router.protocol.IRouter;

public class FlutterRouterWrapper implements IRouter {

    public static FlutterRouterWrapper getInstance() {
        return InnerClass.instance;
    }

    @Override
    public boolean dispatch(String url, Context context) {
        if (!url.startsWith("flutter")) {
            return false;
        }
        FlutterRuntimeUtil.openFlutterContainerWithCachedEngine(context, url);
        return true;
    }

    private static final class InnerClass {
        private static final FlutterRouterWrapper instance = new FlutterRouterWrapper();
    }

}
