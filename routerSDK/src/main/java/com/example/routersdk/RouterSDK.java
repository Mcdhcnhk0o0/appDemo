package com.example.routersdk;

import android.content.Context;
import android.util.Log;

import com.example.router.RouterProcessor;
import com.example.routersdk.bean.ServiceRequest;
import com.example.routersdk.bean.ServiceResponse;
import com.example.routersdk.protocol.IRouter;
import com.example.routersdk.protocol.IService;
import com.example.routersdk.wrapper.LocalServiceWrapper;
import com.example.routersdk.wrapper.NativeRouterWrapper;

import java.util.ArrayList;
import java.util.List;

public final class RouterSDK implements IRouter, IService {

    private static final String TAG = RouterSDK.class.getSimpleName();

    private final List<IRouter> routers = new ArrayList<>();

    private final List<IService> services = new ArrayList<>();

    private RouterSDK() {
        routers.add(NativeRouterWrapper.getInstance());
        services.add(LocalServiceWrapper.getInstance());
    }

    public static RouterSDK getInstance() {
        return InnerClass.instance;
    }

    public void init() { }

    public boolean dispatch(String url) {
        return dispatch(url, null);
    }

    public boolean dispatch(String url, Context context) {
        if (context == null) {
            context = getTopActivity();
        }
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
            Log.e(TAG, e.toString());
        }
    }

    private Context getTopActivity() {
//        if (ActivityManagerService.getTopActivity() != null) {
//            return ActivityManagerService.getTopActivity().get();
//        }
        return null;
    }

    @Override
    public <T> ServiceResponse<T> call(ServiceRequest request) {
        for (IService service: services) {
            ServiceResponse<T> response = service.call(request);
            if (response.isSuccess()) {
                return response;
            }
        }
        return null;
    }

    private static final class InnerClass {
        private static final RouterSDK instance = new RouterSDK();
    }

}
