package com.example.appdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.network.WebSocketService
import com.example.appdemo.network.protocol.WebSocketApi
import com.example.router.annotation.Router


data class ChatModel(val message: String, val fromServer: Boolean)

@Router(url = "native://chat", description = "远程聊天")
class ChatActivity: ComponentActivity(), WebSocketApi {

    private val localMessage = mutableStateOf("")

    private val chatList = mutableStateListOf<ChatModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.LightGray
            ) {
                Column {
                    ChatPage()
                    Spacer(modifier = Modifier.height(50.dp))
                    EditMessageBox()
                    Spacer(modifier = Modifier.height(20.dp))
                    SendButton()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        WebSocketService.getInstance().addMessageListener(this)
    }

    @Preview
    @Composable
    fun ChatPage() {
        Column(
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .border(width = 2.dp, color = Color.Blue, shape = RoundedCornerShape(12.dp))
                .padding(start = 12.dp, end = 12.dp)
        ) {
            chatList.forEachIndexed { _, chatModel ->
                if (chatModel.fromServer) {
                    Text(text = "He/She said: ${chatModel.message}\n")
                } else {
                    Text(text = "You: ${chatModel.message}\n")
                }
            }
        }
    }

    @Composable
    fun EditMessageBox() {
        var myMessage by remember {
            mutableStateOf(localMessage.value)
        }
        TextField(
            value = localMessage.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            onValueChange = { str ->
                myMessage = str
                localMessage.value = str
            },
            placeholder = { Text("请输入你想说的话") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
        )
    }

    @Composable
    fun SendButton() {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            onClick = { sendMessage(localMessage.value) },
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "发送消息", color = Color.White, fontSize = 18.sp)
        }
    }

    private fun sendMessage(message: String) {
        chatList.add(ChatModel(message, false))
        WebSocketService.getInstance().sendMessage(message)
        localMessage.value = ""
    }

    override fun onMessage(message: String) {
        chatList.add(ChatModel(message, true))
    }

}