package com.example.routersdk.bean;


import java.util.LinkedHashMap;

public class ServiceRequest {

    private String serviceName;

    private String methodName;

    private LinkedHashMap<Class<?>, Object> params;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public LinkedHashMap<Class<?>, Object> getParams() {
        return params;
    }

    public void setParams(LinkedHashMap<Class<?>, Object> params) {
        this.params = params;
    }
}

