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
import com.example.appdemo.util.RouterUtil
import com.example.appdemo.util.SharedPrefUtil
import com.example.appdemo.util.ToastUtil
import com.example.router.annotation.Router

@Router(url = "native://login", description = "登录")
class LoginActivity : ComponentActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private val loginHelper = LoginHelper()

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.LightGray
            ) {
                LoginPage()
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun LoginPage() {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            ModeSwitcher()
            Spacer(modifier = Modifier.height(100.dp))
            InputBox()
            Spacer(modifier = Modifier.height(30.dp))
            ActionButton()
            Spacer(modifier = Modifier.height(50.dp))
        }
    }

    @Composable
    fun ModeSwitcher() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .height(40.dp)
                    .width(80.dp),
                onClick = { switchMode() },
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(2.dp, color = Color.Black),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
            ) {
                Text(text = viewModel.getModeTip())
            }
        }
    }

    @Composable
    fun InputBox() {
        Column {
            TextField(
                value = viewModel.email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { str ->
                    viewModel.email = str
                },
                placeholder = { Text("请输入邮箱") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }
            )
            if (viewModel.loginMode == LoginMode.Sign) {
                TextField(
                    value = viewModel.nickname,
                    modifier = Modifier.fillMaxWidth(),
                    onValueChange = { str ->
                        viewModel.nickname = str
                    },
                    placeholder = { Text("请输入昵称") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = null)
                    }
                )
            }
            TextField(
                value = viewModel.password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {str ->
                    viewModel.password = str
                },
                placeholder = { Text("请输入密码") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                }
            )
        }
    }

    @Composable
    fun ActionButton() {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { 
                if (viewModel.loginMode == LoginMode.Login) login() else signUp()
                      },
            shape = RoundedCornerShape(50),
            border = BorderStroke(2.dp, color = Color.Black),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
        ) {
            Text(text = viewModel.getActionTip(), color = Color.Black, fontSize = 18.sp)
        }
    }

    private fun switchMode() {
        if (viewModel.loginMode == LoginMode.Login) {
            viewModel.loginMode = LoginMode.Sign
        } else {
            viewModel.loginMode = LoginMode.Login
        }
    }

    private fun login() {
        loginHelper.login(viewModel.email, viewModel.password,
            object : AbstractApiHelper.ApiResponse<LoginVO> {

                override fun onSuccess(loginVO: LoginVO?) {
                    SharedPrefUtil.setUserIdCache(loginVO?.user?.userId ?: "")
                    SharedPrefUtil.setUserTokenCache(loginVO?.token ?: "")
                    ServiceCreator.refreshToken(loginVO?.token ?: "")
                    RouterUtil.gotoMainPage()
                    ToastUtil.show("登陆成功！")
                }

                override fun onError(message: String?) {
                    ToastUtil.show(message ?: "服务器异常")
                }

                override fun onFail(t: Throwable?) {
                    ToastUtil.show("登陆失败！")
                }

            }
        )
    }

    private fun signUp() {
        loginHelper.signUp(viewModel.email, viewModel.nickname, viewModel.password,
            object : AbstractApiHelper.ApiResponse<LoginVO> {

                override fun onSuccess(loginVO: LoginVO?) {
                    ToastUtil.show("注册成功，请登录")
                    if (viewModel.loginMode == LoginMode.Sign) {
                        switchMode()
                    }
                }

                override fun onError(message: String?) {

                }

                override fun onFail(t: Throwable?) {
                    ToastUtil.show("注册失败，请重试")
                }

            })

    }

}
