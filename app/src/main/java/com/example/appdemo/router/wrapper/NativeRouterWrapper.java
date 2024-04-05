package com.example.appdemo.router.wrapper;

import android.content.Context;
import android.content.Intent;

import com.example.appdemo.router.protocol.IRouter;
import com.example.appdemo.router.protocol.IRouterInterceptor;
import com.example.router.RouterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


public class NativeRouterWrapper implements IRouter {

    private static final String TAG = NativeRouterWrapper.class.getSimpleName();

    private boolean needReSort = false;

    private final List<IRouterInterceptor> routerInterceptors = new ArrayList<>();

    private NativeRouterWrapper() { }

    public static NativeRouterWrapper getInstance() {
        return InnerClass.instance;
    }

    public void addRouterInterceptor(IRouterInterceptor interceptor) {
        routerInterceptors.add(interceptor);
        needReSort = true;
    }

    public void removeRouterInterceptor(IRouterInterceptor interceptor) {
        routerInterceptors.remove(interceptor);
        needReSort = true;
    }

    @Override
    public boolean dispatch(Context context, String url, Map<String, String> params) {
        sortInterceptorsIfNeeded();
        if (beforeDispatch(url)) {
            return false;
        }
        Class<?> destination = RouterManager.getInstance().getClass(url);
        if (destination != null) {
            Intent intent = new Intent(context, destination);
            context.startActivity(intent);
            afterDispatchSucceed(url);
            return true;
        }
        afterDispatchFailed(url);
        return false;
    }

    private boolean beforeDispatch(String url) {
        for (IRouterInterceptor interceptor: routerInterceptors) {
            boolean intercepted = interceptor.beforeRouterDispatch(url);
            if (intercepted) {
                return true;
            }
        }
        return false;
    }

    private void afterDispatchSucceed(String url) {
        for (IRouterInterceptor interceptor: routerInterceptors) {
            interceptor.afterRouterDispatchSucceed(url);
        }
    }

    private void afterDispatchFailed(String url) {
        for (IRouterInterceptor interceptor: routerInterceptors) {
            interceptor.afterRouterDispatchFailed(url);
        }
    }

    private void sortInterceptorsIfNeeded() {
        if (needReSort) {
            Collections.sort(routerInterceptors, (t1, t2) -> Integer.compare(t1.priority(), t2.priority()));
            needReSort = false;
        }
    }

    private static final class InnerClass {
        private static final NativeRouterWrapper instance = new NativeRouterWrapper();
    }

}
