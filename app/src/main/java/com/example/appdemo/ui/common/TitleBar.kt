package com.example.appdemo.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.service.ActivityManagerService
import com.example.appdemo.ui.onClick
import com.example.appdemo.util.StatusBarUtil

@Composable
fun TitleBar(
    title: String,
    rightItem: @Composable () -> Unit
) {
    val titleBarHeight = 40
    Box(
        modifier = Modifier
            .padding(top = StatusBarUtil.getStatusBarHeight().dp)
            .height(titleBarHeight.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(start = 12.dp)
                .width(30.dp)
                .align(Alignment.CenterStart)
        ) {
            Icon(
                modifier = Modifier
                    .fillMaxSize()
                    .onClick {
                        ActivityManagerService
                            .getTopActivity()
                            ?.get()
                            ?.finish()
                    },
                imageVector = Icons.Default.KeyboardArrowLeft,
                contentDescription = null
            )
        }
        Box(modifier = Modifier.align(Alignment.Center)) {
            Text(text = title, fontSize = 20.sp)
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            rightItem()
        }
    }
}


@Preview
@Composable
fun TitleBarPreview() {
    TitleBar(title = "这是标题") {

    }
}