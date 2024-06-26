package com.example.appdemo.ui.page

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.router.OneRouter
import com.example.appdemo.ui.common.BasePage
import com.example.router.RouterManager
import java.util.Locale

data class RouterModel(val name: String, val url: String, val description: String)


@Composable
fun ContentContainerPage() {
    BasePage {
        RouterActionGrid()
    }
}


@Composable
fun RouterActionGrid() {

    val routerList = remember {
        mutableStateListOf<RouterModel>()
    }

    RouterManager.getInstance().routerMap.forEach{
        val uri = Uri.parse(it.key)
        var description = RouterManager.getInstance().routerDescriptionMap[it.key]
        if (description.isNullOrBlank()) {
            description = uri.authority ?: ""
        }
        routerList.add(RouterModel(uri.authority ?: "Unknown", it.key, description))
    }

    routerList.add(RouterModel("routerTest", "", "勿点"))

    LazyVerticalGrid(columns = GridCells.Fixed(4)) {
        items(routerList.size) {i ->
            val contentChar = routerList[i].name.substring(0, 2).uppercase(Locale.CHINA)
            var description = routerList[i].description
            if (description.isBlank()) {
                description = routerList[i].name
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            OneRouter.getInstance().dispatch(routerList[i].url)
                        }
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .height(48.dp)
                        .background(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = contentChar, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = description)
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
        routerList.add(RouterModel(uri.authority ?: "Unknown", it.key, ""))
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
                    OneRouter.getInstance().dispatch(routerModel.url)
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