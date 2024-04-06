package com.example.appdemo.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appdemo.activity.home.ui.home.UserViewModel
import com.example.appdemo.activity.viewmodel.HomeViewModel
import com.example.appdemo.ui.common.BasePage
import com.example.appdemo.ui.common.BottomSheetPage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContainerPage(
    homeViewModel: HomeViewModel,
    userViewModel: UserViewModel
) {
    BasePage {
        BottomSheetPage(
            sheet = { scope, state ->
                SheetContent(scope = scope, bottomSheetState = state, viewModel = homeViewModel)
            }
        ) { scope, state ->
            HomePageContent(
                scope = scope,
                bottomSheetState = state,
                userViewModel = userViewModel,
                homeViewModel = homeViewModel
            )
        }
    }

}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePageContent(
    scope: CoroutineScope,
    bottomSheetState: BottomSheetState,
    userViewModel: UserViewModel,
    homeViewModel: HomeViewModel
) {
    Column {
        Row(
            modifier = Modifier.height(100.dp)
        ) {
            Button(
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                onClick = { homeViewModel.changeEnvironment() }
            ) {
                Text(text = if (homeViewModel.debugMode) "切换至远程环境" else "切换至本地环境")
            }
            Button(
                onClick = { scope.launch { bottomSheetState.expand() } },
                modifier = Modifier
                    .padding(start = 20.dp)
                    .width(150.dp)
                    .height(50.dp)
            ) {
                Text(text = "调整debugUrl")
            }
        }

        Column(
            modifier = Modifier.height(100.dp)
        ) {
            Text(text = "调试模式：${homeViewModel.getDebugModeDescription()}")
            Text(text = "登录状态：${userViewModel.loginStatus}")
        }
        Box(
            modifier = Modifier.height(200.dp)
        ) {
            Column {
                Text(text = "用户ID：${userViewModel.loginUserModel.user?.userId}")
                Text(text = "用户名：${userViewModel.loginUserModel.user?.userName}")
                Text(text = "邮箱：${userViewModel.loginUserModel.user?.email}")
                Text(text = "性别：${userViewModel.loginUserModel.detail?.gender}")
                Text(text = "地址：${userViewModel.loginUserModel.detail?.address}")
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SheetContent(
    scope: CoroutineScope,
    bottomSheetState: BottomSheetState,
    viewModel: HomeViewModel
) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .height(300.dp)
    ) {
        Text(text = "当前debugUrl为：${viewModel.debugUrl}")
        DebugUrlInput(viewModel)
        Button(
            onClick = { scope.launch { bottomSheetState.collapse() } },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "退出")
        }
        Button(
            onClick = {
                scope.launch {
                    viewModel.changeDebugUrl()
                    bottomSheetState.collapse()
                }
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(text = "保存并退出")
        }
    }
}


@Composable
fun DebugUrlInput(
    viewModel: HomeViewModel
) {
    Row {
        TextField(
            value = viewModel.debugAddress,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { str ->
                viewModel.debugAddress = str
            },
            placeholder = { Text("形如192.168.0.102，本地调试下生效") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Create, contentDescription = null)
            }
        )
    }
}