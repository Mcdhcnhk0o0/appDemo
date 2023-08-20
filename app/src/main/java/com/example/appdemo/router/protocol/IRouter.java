package com.example.appdemo.router.protocol;

import android.content.Context;

public interface IRouter {

    boolean dispatch(String url, Context context);

}
