package com.example.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.router.annotation.Router;

@Router(url = "native://home/test", group = "home", description = "首页测试")
public class TestHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_home);
    }
}