package com.example.appdemo.ui.page

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.activity.viewmodel.DebugViewModel
import com.example.appdemo.ui.common.BasePage
import com.example.appdemo.ui.common.BottomSheetPage
import com.example.appdemo.ui.common.TitleBar
import com.example.appdemo.ui.onClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


data class DebugOption(
    val description: String,
    val tip: String?,
    val onTap: () -> Unit
)


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DebugOptionPage(
    viewModel: DebugViewModel
) {
    var ipType by remember {
        mutableStateOf("")
    }
    var bottomSheetDelegate by remember {
        mutableStateOf(false)
    }
    val debugOptions = listOf(
        DebugOption("Debug开关", viewModel.getDebugModeDescription()) {
            viewModel.changeEnvironment()
            bottomSheetDelegate = false
        },
        DebugOption("本地调试链接", viewModel.localIp) {
            ipType = "local"
            bottomSheetDelegate = true
        },
        DebugOption("远程调试链接", viewModel.remoteIp) {
            ipType = "remote"
            bottomSheetDelegate = true
        }
    )
    BasePage(
        autoTopPadding = false
    ) {
        BottomSheetPage(
            sheet = { scope, bottomSheetState ->
                IPModifiedSheet(
                    scope = scope,
                    bottomSheetState = bottomSheetState,
                    viewModel = viewModel,
                    ipType = ipType
                )
            }
        ) { scope, state ->
            DebugOptionList(
                debugOptions = debugOptions,
                scope = scope,
                bottomSheetState = state,
                bottomSheetDelegate = bottomSheetDelegate
            )
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DebugOptionList(
    debugOptions: List<DebugOption>,
    scope: CoroutineScope,
    bottomSheetState: BottomSheetState,
    bottomSheetDelegate: Boolean
) {
    val columnState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .onClick {
                if (bottomSheetState.isExpanded) {
                    scope.launch { bottomSheetState.collapse() }
                }
            }
    ) {
        TitleBar(title = "调试选项") {

        }
        LazyColumn(
            state = columnState,
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 20.dp)
        ) {
            items(debugOptions.size) { index: Int ->
                val option = debugOptions[index]
                DebugOptionCell(
                    description = option.description,
                    tip = option.tip,
                    onTap = {
                        option.onTap()
                        if (bottomSheetDelegate) {
                            scope.launch {
                                bottomSheetState.expand()
                            }
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun DebugOptionCell(
    description: String,
    tip: String? = null,
    onTap: () -> Unit,
    status: @Composable () -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp)
        .clickable(
            onClick = { onTap() }
        )
    ) {
        Box(modifier = Modifier.align(Alignment.CenterStart)) {
            Text(text = description, fontSize = 16.sp)
        }
        Box(modifier = Modifier.align(Alignment.CenterEnd)) {
            Text(text = tip ?: "", fontSize = 14.sp, color = Color.Gray)
        }
        status()
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IPModifiedSheet(
    scope: CoroutineScope,
    bottomSheetState: BottomSheetState,
    viewModel: DebugViewModel,
    ipType: String
) {
    val ipAddress = if (ipType == "local") viewModel.localIp else viewModel.remoteIp
    var newIPAddress by remember {
        mutableStateOf(ipAddress)
    }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(200.dp)
    ) {
        Text(text = "当前使用的IP地址为：$ipAddress")
        Box(modifier = Modifier.height(20.dp))
        IPInputField(
            value = newIPAddress,
            onValueChange = { it ->
                newIPAddress = it
            }
        )
        Row {
            Button(
                onClick = {
                    scope.launch {
                        if (ipType == "local") {
                            viewModel.changeLocalIP(newIPAddress)
                        } else {
                            viewModel.changeRemoteIP(newIPAddress)
                        }
                        bottomSheetState.collapse()
                    }
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "保存并退出")
            }
            Box(modifier = Modifier.width(100.dp))
            Button(
                onClick = { scope.launch { bottomSheetState.collapse() } },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "退出")
            }

        }
    }
}


@Composable
fun IPInputField(
    value: String,
    onValueChange: (String) -> Unit,
) {
    Row {
        TextField(
            value = value,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onValueChange,
            placeholder = { Text("形如192.168.0.102") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Create, contentDescription = null)
            }
        )
    }
}