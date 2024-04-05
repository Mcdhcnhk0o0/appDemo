package com.example.router;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceManager {

    private final Map<String, String> serviceMap = new HashMap<>();

    private final Map<String, List<String>> methodMap = new HashMap<>();

    public static ServiceManager getInstance() {
        return InnerClass.instance;
    }

    private ServiceManager() { }

    public void register(String name, String service) {
        if (name != null && service != null) {
            serviceMap.put(name, service);
        }
    }

    public void addMethod(String service, String methodName) {
        if (service != null && methodName != null) {
            if (methodMap.get(service) != null) {
                methodMap.get(service).add(methodName);
            } else {
                List<String> methodList = new ArrayList<>();
                methodList.add(methodName);
                methodMap.put(service, methodList);
            }
        }
    }

    public String getService(String service) {
        return serviceMap.get(service);
    }

    public List<String> getMethods(String serviceClassName) {
        return methodMap.get(serviceClassName);
    }

    public Object callService(String service, String method) {
        return callService(service, method, new Class[0], new Object[0]);
    }

    public Object callService(String service, String method, Class<?>[] paramTypes, Object[] paramsValues) {
        try {
            if (serviceMap.containsKey(service)) {
                service = serviceMap.get(service);
            }
            Class<?> clazz = Class.forName(service);
            Object instance = clazz.getConstructor().newInstance();
            Method targetMethod = clazz.getMethod(method, paramTypes);
            return targetMethod.invoke(instance, paramsValues);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static final class InnerClass {
        private static final ServiceManager instance = new ServiceManager();
    }

}
