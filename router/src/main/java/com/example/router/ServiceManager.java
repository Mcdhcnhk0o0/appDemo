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

    public Map<String, Object> callService(String service, String method) {
        return callService(service, method, new Class[0], new Object[0]);
    }

    public Map<String, Object> callService(String service, String method, Class<?>[] paramTypes, Object[] paramsValues) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (serviceMap.containsKey(service)) {
                service = serviceMap.get(service);
            }
            Class<?> clazz = Class.forName(service);
            Object instance = clazz.getConstructor().newInstance();
            Method targetMethod = clazz.getMethod(method, paramTypes);
            Object methodResult = targetMethod.invoke(instance, paramsValues);
            result.put("success", true);
            result.put("result", methodResult);
        } catch (Exception e) {
            result.put("success", false);
            result.put("result", e);
        }
        return result;
    }

    private static final class InnerClass {
        private static final ServiceManager instance = new ServiceManager();
    }

}
