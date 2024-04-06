package com.example.appdemo.ui.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appdemo.activity.home.ui.home.UserViewModel
import com.example.appdemo.activity.viewmodel.HomeViewModel
import com.example.appdemo.ui.common.BasePage
import com.example.appdemo.ui.common.BottomSheetPage
import kotlinx.coroutines.CoroutineScope


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

}
