package com.example.appdemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appdemo.flutter.FlutterRuntimeUtil
import com.example.appdemo.ui.theme.AppDemoTheme
import com.example.sleep.MainActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(name = "Android")
                        ActionButton()
                    }
                }
            }
        }
        FlutterRuntimeUtil.initFlutterEngineWithContext(this)
    }

    @Composable
    fun ActionButton() {
        Button(
            onClick = {
                val intent = Intent(MainActivity@this, MainActivity::class.java)
                startActivity(intent)
//                FlutterRuntimeUtil.openFlutterContainerWithCachedEngine(MainActivity@this, "")
            }
        ) {
            Text(text = "Click me to open Flutter!")
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello $name!",
        modifier = Modifier.fillMaxWidth().padding(top = 30.dp, bottom = 10.dp),
        textAlign = TextAlign.Center
    )
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AppDemoTheme {
        Greeting("Android")
    }
}