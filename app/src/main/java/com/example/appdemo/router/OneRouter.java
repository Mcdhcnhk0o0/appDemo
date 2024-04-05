package com.example.appdemo.router;

import android.content.Context;
import android.util.Log;

import com.example.appdemo.router.protocol.IRouter;
import com.example.appdemo.router.protocol.IRouterInterceptor;
import com.example.appdemo.router.wrapper.FlutterRouterWrapper;
import com.example.appdemo.router.wrapper.NativeRouterWrapper;
import com.example.appdemo.service.ActivityManagerService;
import com.example.router.RouterProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class OneRouter implements IRouter {

    private static final String TAG = OneRouter.class.getSimpleName();

    private final List<IRouter> routers = new ArrayList<>();

    private OneRouter() {
        routers.add(NativeRouterWrapper.getInstance());
        routers.add(FlutterRouterWrapper.getInstance());
    }

    public static OneRouter getInstance() {
        return InnerClass.instance;
    }

    public void init() {
        NativeRouterWrapper.getInstance().addRouterInterceptor(new IRouterInterceptor() {
            @Override
            public boolean beforeRouterDispatch(String url) {
                return !url.startsWith("native");
            }

            @Override
            public void afterRouterDispatchSucceed(String url) {

            }

            @Override
            public void afterRouterDispatchFailed(String url) {

            }

            @Override
            public int priority() {
                return 0;
            }
        });
    }

    public boolean dispatch(String url) {
        return dispatch(null, url, null);
    }

    public boolean dispatch(String url, Map<String, String> params) {
        return dispatch(null, url, params);
    }

    public boolean dispatch(Context context, String url, Map<String, String> params) {
        if (context == null) {
            context = getTopActivity();
        }
        for (IRouter router: routers) {
            if (router.dispatch(context, url, params)) {
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
            Log.e(TAG, e.toString());
        }
    }

    private Context getTopActivity() {
        if (ActivityManagerService.getTopActivity() != null) {
            return ActivityManagerService.getTopActivity().get();
        }
        return null;
    }

    private static final class InnerClass {
        private static final OneRouter instance = new OneRouter();
    }

}
