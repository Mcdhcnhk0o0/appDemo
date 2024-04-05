package com.example.appdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.activity.viewmodel.LoginMode
import com.example.appdemo.activity.viewmodel.LoginViewModel
import com.example.appdemo.network.ServiceCreator
import com.example.appdemo.network.helper.AbstractApiHelper
import com.example.appdemo.network.helper.LoginHelper
import com.example.appdemo.pojo.vo.LoginVO
import com.example.appdemo.router.OneRouter
import com.example.appdemo.util.RouterUtil
import com.example.appdemo.util.SharedPrefUtil
import com.example.appdemo.util.ToastUtil
import com.example.router.annotation.Router


@Router(url = "native://setting", description = "设置")
class SettingActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
        OneRouter.getInstance().dispatch("flutter://demo1")
    }

}