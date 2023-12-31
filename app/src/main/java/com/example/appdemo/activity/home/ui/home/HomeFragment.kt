package com.example.appdemo.activity.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.appdemo.activity.viewmodel.HomeViewModel
import com.example.appdemo.network.ServiceCreator
import com.example.appdemo.network.helper.AbstractApiHelper.ApiResponse
import com.example.appdemo.network.helper.UserInfoHelper
import com.example.appdemo.pojo.vo.UserInfoVO
import com.example.appdemo.util.SharedPrefUtil
import com.example.appdemo.util.ToastUtil
import com.example.appdemo.util.UserInfoUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private val userInfoHelper = UserInfoHelper()

    private val homeViewModel by viewModels<HomeViewModel>()

    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            initUserStatus()
            setContent {
                BottomSheetExample()
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BottomSheetExample() {
        val scaffoldState = rememberBottomSheetScaffoldState()
        val bottomSheetState = scaffoldState.bottomSheetState
        val scope = rememberCoroutineScope()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContent = {
               SheetContent(scope = scope, bottomSheetState = bottomSheetState)
            },
            sheetPeekHeight = 0.dp
        ) {
            // 页面内容
            HomePageContent(scope = scope, bottomSheetState = bottomSheetState)
        }
    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.needRefresh) {
            initUserStatus()
            homeViewModel.needRefresh = false
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun SheetContent(scope: CoroutineScope, bottomSheetState: BottomSheetState) {
        Column(modifier = Modifier
            .padding(16.dp)
            .height(300.dp)) {
            Text(text = "当前debugUrl为：${homeViewModel.debugUrl}")
            DebugUrlInput()
            Button(
                onClick = { scope.launch { bottomSheetState.collapse() } },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "退出")
            }
            Button(
                onClick = { scope.launch {
                    changeDebugUrl()
                    bottomSheetState.collapse()
                } },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text(text = "保存并退出")
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun HomePageContent(scope: CoroutineScope, bottomSheetState: BottomSheetState) {
        Column {
                Row(
                    modifier = Modifier.height(100.dp)
                ) {
                    Button(
                        modifier = Modifier
                            .width(150.dp)
                            .height(50.dp),
                        onClick = { changeEnvironment() }
                    ) {
                        Text(text = if(homeViewModel.debugMode) "切换至远程环境" else "切换至本地环境")
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

    @Composable
    fun DebugUrlInput() {
        Row {
            TextField(
                value = homeViewModel.debugAddress,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { str ->
                    homeViewModel.debugAddress = str
                },
                placeholder = { Text("形如192.168.0.102，本地调试下生效") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Create, contentDescription = null)
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initUserStatus() {
        homeViewModel.debugMode = SharedPrefUtil.isBaseUrlInDebugMode()
        userViewModel.loginStatus = UserInfoUtil.getUserToken().isNotBlank() and UserInfoUtil.getUserId().isNotBlank()
        if (!userViewModel.loginStatus) {
            return
        }
        userInfoHelper.getUserInfo(UserInfoUtil.getUserId(), object : ApiResponse<UserInfoVO> {
            override fun onSuccess(userInfoVO: UserInfoVO?) {
                userViewModel.loginUserModel = userInfoVO!!
            }

            override fun onFail(t: Throwable?) {
                ToastUtil.show("用户信息获取失败！")
            }

        })
    }

    private fun changeEnvironment() {
        homeViewModel.debugMode = !homeViewModel.debugMode
        if (homeViewModel.debugMode) {
            SharedPrefUtil.applyDebugUrlSetting()
        } else {
            SharedPrefUtil.applyReleaseUrlSetting()
        }
        ServiceCreator.refreshToken(UserInfoUtil.getUserToken())
    }

    private fun changeDebugUrl() {
        if (homeViewModel.debugAddress.isNotBlank()) {
            val newDebugUrl = "http://${homeViewModel.debugAddress}:8880/demo/"
            homeViewModel.debugUrl = newDebugUrl
            ServiceCreator.refreshDebugUrl(newDebugUrl, needApply = false)
        }
    }

}