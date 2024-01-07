package com.example.routersdk.protocol;

import android.content.Context;

public interface IRouter {

    boolean dispatch(String url, Context context);

}
