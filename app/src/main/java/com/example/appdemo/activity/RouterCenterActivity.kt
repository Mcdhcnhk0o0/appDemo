package com.example.appdemo.activity

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.activity.ui.theme.AppDemoTheme
import com.example.appdemo.router.OneRouter
import com.example.router.RouterManager
import com.example.router.annotation.Router

data class RouterModel(val name: String, val url: String)


@Router("native://router_center")
class RouterCenterActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RouterActionList()
                }
            }
        }
    }

    @Preview
    @Composable
    fun RouterActionList() {

        val routerList = remember {
            mutableStateListOf<RouterModel>()
        }

        RouterManager.getInstance().routerMap.forEach{
            val uri = Uri.parse(it.key)
            routerList.add(RouterModel(uri.authority ?: "Unknown", it.key))
        }

        Log.d("RouterCenterActivity", "size: " + RouterManager.getInstance().routerMap.size)

        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
        ) {
            routerList.forEachIndexed { i, routerModel ->
                Button(
                    onClick = {
                        OneRouter.getInstance().dispatch(routerModel.url, this@RouterCenterActivity)
                    },
                    modifier = Modifier
                        .padding(start = 12.dp, end = 12.dp)
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = "${i + 1}. ${routerModel.name}",
                        fontSize = 16.sp
                    )
                }
            }

        }

    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    AppDemoTheme {
        Greeting2("Android")
    }
}