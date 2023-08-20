package com.example.appdemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.appdemo.R;
import com.example.router.annotation.Router;

@Router("native://java_native")
public class JavaRouterTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_router_test);
    }
}