package com.example.appdemo.router.protocol;

public interface IRouterInterceptor {

    boolean beforeRouterDispatch();

    void afterRouterDispatchSucceed();

    void afterRouterDispatchFailed();

    int priority();

}
