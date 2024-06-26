package com.example.appdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.appdemo.activity.RouterCenterActivity
import com.example.appdemo.flutter.FlutterRuntimeUtil
import com.example.appdemo.router.OneRouter
import com.example.appdemo.ui.theme.AppDemoTheme
import com.example.router.annotation.Router


@Router(url = "native://main", description = "主函数")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(name = "Android")
                        NativeActionButton()
                        FlutterActionButton()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        OneRouter.getInstance().dispatch("native://home")
        finish()
    }

    @Composable
    fun NativeActionButton() {
        Button(
            onClick = {
                val intent = Intent(this, RouterCenterActivity::class.java)
                startActivity(intent)
//                FlutterRuntimeUtil.openFlutterContainerWithCachedEngine(MainActivity@this, "")
            }
        ) {
            Text(text = "Click me to open RouterCenter!")
        }
    }

    @Composable
    fun FlutterActionButton() {
        Button(
            onClick = {
                FlutterRuntimeUtil.openFlutterContainerWithCachedEngine(this, "")
            }
        ) {
            Text(text = "Click me to open FlutterContainer!")
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp, bottom = 10.dp),
        textAlign = TextAlign.Center
    )
}

