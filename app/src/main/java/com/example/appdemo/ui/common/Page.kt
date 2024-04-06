package com.example.appdemo.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appdemo.R
import com.example.appdemo.util.StatusBarUtil


@Composable
fun BasePage(
    content: @Composable () -> Unit
) {
    val statusBarHeight = StatusBarUtil.getStatusBarHeight()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEEF5FB))
            .padding(top = statusBarHeight.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.page_background),
                contentDescription = null
            )
        }
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun BasePagePreview() {
    BasePage {

    }
}