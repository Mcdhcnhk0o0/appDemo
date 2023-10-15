package com.example.appdemo.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.network.response.Result
import com.example.appdemo.network.response.LoginResult
import com.example.appdemo.network.service.LoginService
import com.example.appdemo.ui.theme.AppDemoTheme
import com.example.router.annotation.Router
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Router(url = "native://login")
class LoginActivity : ComponentActivity() {

    companion object {
        private const val TAG = "LoginActivity"
    }

    private var loginEmail: String = ""
    private var loginPassword: String = ""
    private var loginResult = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    LoginPage()
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun LoginPage() {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            LoginInputBox()
            Spacer(modifier = Modifier.height(30.dp))
            LoginButton()
            Spacer(modifier = Modifier.height(50.dp))
            LoginResultArea()
        }
    }

    @Composable
    fun LoginInputBox() {
        var email by remember {
            mutableStateOf(loginEmail)
        }
        var password by remember {
            mutableStateOf(loginPassword)
        }
        Column {
            TextField(
                value = email,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { str ->
                    email = str
                    loginEmail = str
                },
                placeholder = { Text("请输入邮箱") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = null)
                }
            )
            TextField(
                value = password,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = {str ->
                    password = str
                    loginPassword = str
                },
                placeholder = { Text("请输入密码") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                }
            )
        }
    }

    @Composable
    fun LoginButton() {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { login() },
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "登录", color = Color.White, fontSize = 18.sp)
        }
    }

    @Composable
    fun LoginResultArea() {
        Box(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .background(color = Color.Yellow),
        ) {
            Text(text = loginResult.value)
        }
    }

    private fun login() {
        LoginService.getService().loginByEmail(loginEmail, loginPassword)
            .enqueue(object: Callback<Result<LoginResult>> {

                override fun onResponse(
                    call: Call<Result<LoginResult>>,
                    response: Response<Result<LoginResult>>
                ) {
                    loginResult.value = "success! info: \n  ${response.body()?.data.toString()}"
                    runOnUiThread {
                        Toast.makeText(this@LoginActivity, "登陆成功！", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Result<LoginResult>>, t: Throwable) {
                    loginResult.value = "errors $t in ${call.request()}"
                    Log.e(TAG, "login failed! Return: $call")
                }

            })
    }

}
