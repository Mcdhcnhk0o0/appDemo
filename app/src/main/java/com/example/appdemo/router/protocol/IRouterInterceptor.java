package com.example.appdemo.router.protocol;

public interface IRouterInterceptor {

    boolean beforeRouterDispatch(String url);

    void afterRouterDispatchSucceed(String url);

    void afterRouterDispatchFailed(String url);

    int priority();

}
