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

    public String callService(String service, String method, List<Class<?>> paramTypes, List<Object> paramsValues) {
        JSONObject result = new JSONObject();
        try {
            Class<?> clazz = Class.forName(serviceMap.get(service));
            Object instance = clazz.getConstructor().newInstance();
            Method targetMethod = clazz.getMethod(method, list2Array(paramTypes));
            Object methodResult = targetMethod.invoke(instance, list2Array(paramsValues));
            result.put("success", true);
            result.put("result", JSON.toJSONString(methodResult));
        } catch (Exception e) {
            result.put("success", true);
            result.put("result", JSON.toJSONString(e));
        }
        return JSON.toJSONString(result);
    }

    private static <T> T[] list2Array(List<T> data) {
        if (data == null || data.size() == 0) {
            return (T[]) new Object[0];
        }
        T[] array = (T[]) new Object[data.size()];
        for (int i = 0; i < data.size(); i++) {
            array[i] = data.get(i);
        }
        return array;
    }

    private static final class InnerClass {
        private static final ServiceManager instance = new ServiceManager();
    }

}
