package com.example.appdemo.router.protocol;

import android.content.Context;

import androidx.annotation.Nullable;

import java.util.Map;

public interface IRouter {

    boolean dispatch(Context context, String url, @Nullable Map<String, String> params);

}
