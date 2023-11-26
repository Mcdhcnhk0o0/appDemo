package com.example.appdemo.activity.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.appdemo.activity.viewmodel.HomeViewModel
import com.example.appdemo.network.ServiceCreator
import com.example.appdemo.network.helper.UserInfoHelper
import com.example.appdemo.pojo.vo.UserInfoVO
import com.example.appdemo.util.SharedPrefUtil
import com.example.appdemo.util.ToastUtil
import com.example.appdemo.util.UserInfoUtil


class HomeFragment : Fragment() {

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
                Column {
                    Button(onClick = { changeEnvironment() }) {
                        Text(text = homeViewModel.getDebugModeDescription())
                    }
                    Box(
                        modifier = Modifier.height(100.dp)
                    ) {
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
        }
    }

    override fun onResume() {
        super.onResume()
        if (homeViewModel.needRefresh) {
            initUserStatus()
            homeViewModel.needRefresh = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initUserStatus() {
        homeViewModel.debugMode = SharedPrefUtil.isInDebugMode()
        userViewModel.loginStatus = UserInfoUtil.getUserToken().isNotBlank() and UserInfoUtil.getUserId().isNotBlank()
        if (!userViewModel.loginStatus) {
            return
        }
        UserInfoHelper.getUserInfo(UserInfoUtil.getUserId(), object : UserInfoHelper.UserInfoResponse {
            override fun onSuccess(userInfoVO: UserInfoVO?) {
                userViewModel.loginUserModel = userInfoVO!!
            }

            override fun onError(message: String?) {
            }

            override fun onFail(t: Throwable?) {
                ToastUtil.show("用户信息获取失败！")
            }

        })
    }

    private fun changeEnvironment() {
        homeViewModel.debugMode = !homeViewModel.debugMode
        if (homeViewModel.debugMode) {
            SharedPrefUtil.setDebugBaseUrl()
        } else {
            SharedPrefUtil.setReleaseBaseUrl()
        }
        ServiceCreator.refreshToken(UserInfoUtil.getUserToken())
    }

}