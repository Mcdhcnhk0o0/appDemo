package com.example.appdemo.activity.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appdemo.R;
import com.example.router.annotation.Router;

@Router(url = "native://java_native", description = "路由测试")
public class JavaRouterTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_router_test);
    }
}