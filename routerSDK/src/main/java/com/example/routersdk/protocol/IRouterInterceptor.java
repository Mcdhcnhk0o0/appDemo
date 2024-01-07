package com.example.routersdk.protocol;

public interface IRouterInterceptor {

    boolean beforeRouterDispatch();

    void afterRouterDispatchSucceed();

    void afterRouterDispatchFailed();

    int priority();

}
