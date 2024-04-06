package com.example.appdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.appdemo.activity.viewmodel.DebugViewModel
import com.example.appdemo.ui.common.BasePage
import com.example.appdemo.ui.page.DebugOptionPage
import com.example.appdemo.util.StatusBarUtil
import com.example.router.annotation.Router


@Router(url = "native://debug_option", description = "调试选项")
class DebugActivity: ComponentActivity() {

    private val viewModel by viewModels<DebugViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.allowLayoutBehindStatusBar()
        StatusBarUtil.changeStatusBarDarkMode()
        setContent {
            DebugOptionPage(viewModel)
        }
    }

}