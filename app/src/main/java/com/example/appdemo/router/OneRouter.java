package com.example.appdemo.router;

import android.content.Context;
import android.util.Log;

import com.example.appdemo.router.protocol.IRouter;
import com.example.appdemo.router.wrapper.FlutterRouterWrapper;
import com.example.appdemo.router.wrapper.NativeRouterWrapper;
import com.example.router.RouterProcessor;

import java.util.ArrayList;
import java.util.List;

public final class OneRouter implements IRouter {

    private static final String TAG = OneRouter.class.getSimpleName();

    private List<IRouter> routers = new ArrayList<>();

    private OneRouter() {
        routers.add(NativeRouterWrapper.getInstance());
        routers.add(FlutterRouterWrapper.getInstance());
    }

    public static OneRouter getInstance() {
        return InnerClass.instance;
    }

    public void init() { }

    public boolean dispatch(String url, Context context) {
        for (IRouter router: routers) {
            if (router.dispatch(url, context)) {
                return true;
            }
        }
        return false;
    }

    public void registerNativeRouterModules(String groupName) {
        String fullName = String.format("%s.%s.%s", RouterProcessor.INIT_PACKAGE, groupName, RouterProcessor.INIT_CLASS);
        try {
            Class.forName(fullName).getMethod(RouterProcessor.INIT_METHOD).invoke(null);
            Log.d(TAG, "Router in " + fullName + " is registered!");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }

    private static final class InnerClass {
        private static final OneRouter instance = new OneRouter();
    }

}
