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
import com.example.appdemo.ui.page.HomeContainerPage
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
                HomeContainerPage(homeViewModel = homeViewModel, userViewModel = userViewModel)
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

}