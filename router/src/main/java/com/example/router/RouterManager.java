package com.example.router;

import java.util.HashMap;
import java.util.Map;

public class RouterManager {

    private final Map<String, String> routerMap = new HashMap<>();
    private final Map<String, String> routerDescriptionMap = new HashMap<>();

    public static RouterManager getInstance() {
        return InnerClass.instance;
    }

    private RouterManager() { }

    public void register(String url, String className) {
        if (url != null && className != null) {
            routerMap.put(url, className);
        }
    }

    public void addDescription(String url, String description) {
        if (url != null && description != null) {
            routerDescriptionMap.put(url, description);
        }
    }

    public Class<?> getClass(String url) {
        if (!routerMap.containsKey(url)) {
            return null;
        }
        Class<?> clazz = null;
        try {
            clazz = Class.forName(routerMap.get(url));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clazz;
    }

    public Map<String, String> getRouterMap() {
        return routerMap;
    }

    public Map<String, String> getRouterDescriptionMap() {
        return routerDescriptionMap;
    }

    private static final class InnerClass {
        private static final RouterManager instance = new RouterManager();
    }

}
