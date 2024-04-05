package com.example.appdemo

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import com.example.appdemo.router.OneRouter
import com.example.appdemo.util.StatusBarUtil
import com.example.router.annotation.Router
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Router(url = "native://launcher", description = "启动页")
class LauncherActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        requestWindowFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        StatusBarUtil.allowLayoutBehindStatusBar()
        StatusBarUtil.changeStatusBarDarkMode()
        StatusBarUtil.wantToGetStatusBarHeight(this)
        super.onCreate(savedInstanceState)

        setContent{
            LauncherPage()
        }
    }
    
    @Composable
    fun LauncherPage() {
        Button(onClick = { gotoMainPage() }) {
            Text(text = "跳过")
        }

    }

    private fun gotoMainPage() {
        CoroutineScope(Dispatchers.IO).launch {
            delay(3000)
            OneRouter.getInstance().dispatch("native://main")
        }
    }
    
}