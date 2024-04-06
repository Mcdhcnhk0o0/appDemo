package com.example.appdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.appdemo.ui.common.BasePage
import com.example.appdemo.ui.page.DebugOptionPage
import com.example.appdemo.util.StatusBarUtil
import com.example.router.annotation.Router


@Router(url = "native://debug_option", description = "调试选项")
class DebugActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.allowLayoutBehindStatusBar()
        StatusBarUtil.changeStatusBarDarkMode()
        setContent {
            BasePage {
                DebugOptionPage()
            }
        }
    }

}