package com.example.appdemo.activity.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.appdemo.R
import com.example.router.annotation.Router

@Router(url = "native://kotlin_native", description = "路由测试")
class KotlinRouterTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_router_test)
    }
}