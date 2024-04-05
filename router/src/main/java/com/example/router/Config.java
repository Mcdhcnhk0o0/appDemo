package com.example.router;

import com.example.router.processor.RouterSubProcessor;
import com.example.router.processor.ServiceSubProcessor;
import com.example.router.processor.SubProcessor;

import java.util.ArrayList;
import java.util.List;

public class Config {

    public static String ROUTER_CONFIG_CLASS = "RouterInit";
    public static String SERVICE_CONFIG_CLASS = "ServiceInit";

    public static String CONFIG_PACKAGE = "com.example";

    public static String INITIALIZE_METHOD = "init";

    public static List<SubProcessor> subProcessors = new ArrayList<>();

    static {
        subProcessors.add(new ServiceSubProcessor());
        subProcessors.add(new RouterSubProcessor());
    }

}
