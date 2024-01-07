package com.example.appdemo.router.wrapper;

import android.content.Context;
import android.content.Intent;

import com.example.appdemo.router.protocol.IRouter;
import com.example.appdemo.router.protocol.IRouterInterceptor;
import com.example.router.RouterManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NativeRouterWrapper implements IRouter {

    private static final String TAG = NativeRouterWrapper.class.getSimpleName();

    private boolean needReSort = false;

    private List<IRouterInterceptor> routerInterceptors = new ArrayList<>();

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
    public boolean dispatch(String url, Context context) {
        sortInterceptorsIfNeeded();
        if (beforeDispatch()) {
            return false;
        }
        Class<?> destination = RouterManager.getInstance().getClass(url);
        if (destination != null) {
            Intent intent = new Intent(context, destination);
            context.startActivity(intent);
            afterDispatchSucceed();
            return true;
        }
        afterDispatchFailed();
        return false;
    }

    private boolean beforeDispatch() {
        for (IRouterInterceptor interceptor: routerInterceptors) {
            boolean intercepted = interceptor.beforeRouterDispatch();
            if (intercepted) {
                return true;
            }
        }
        return false;
    }

    private void afterDispatchSucceed() {
        for (IRouterInterceptor interceptor: routerInterceptors) {
            interceptor.afterRouterDispatchSucceed();
        }
    }

    private void afterDispatchFailed() {
        for (IRouterInterceptor interceptor: routerInterceptors) {
            interceptor.afterRouterDispatchFailed();
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
