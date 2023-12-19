package com.example.appdemo.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appdemo.activity.viewmodel.LLMItemModel
import com.example.appdemo.activity.viewmodel.LLMViewModel
import com.example.appdemo.network.helper.AbstractApiHelper.ApiResponse
import com.example.appdemo.network.helper.ChatHelper
import com.example.appdemo.pojo.vo.ChatHistoryVO
import com.example.appdemo.util.ToastUtil
import com.example.appdemo.util.UserInfoUtil
import com.example.router.annotation.Router


@Router(url = "native://llm", description = "大模型对话")

class LLMActivity: ComponentActivity() {

    private val chatHelper = ChatHelper()

    private val llmViewModel by viewModels<LLMViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.LightGray
            ) {
                Column {
                    HistoryButton()
                    ChatPage()
                    Spacer(modifier = Modifier.height(50.dp))
                    EditMessageBox()
                    Spacer(modifier = Modifier.height(20.dp))
                    SendButton()
                }
            }
        }
    }

    @Composable
    fun HistoryButton() {
        Button(onClick = { getChatHistory() }) {
            Text(text = "获取对话历史")
        }
    }

    @Composable
    fun ChatPage() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            llmViewModel.currentChatList.forEach { llmItemModel ->
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .background(
                            color = Color.DarkGray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 20.dp)
                ) {
                    Text(text = llmItemModel.content)
                }
            }
        }
    }

    @Composable
    fun EditMessageBox() {
        TextField(
            value = llmViewModel.currentMessage,
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            onValueChange = { str ->
                llmViewModel.currentMessage = str
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
            onClick = { sendQueryToRemote() },
            shape = RoundedCornerShape(50)
        ) {
            Text(text = "发送消息", color = Color.White, fontSize = 18.sp)
        }
    }

    private fun getRemoteConnection() {
        llmViewModel.waitingForResponse = true
        chatHelper.startLLMConnection {
            if (it) {
                llmViewModel.connectionEstablished = true
                ToastUtil.show("远程连接已建立")
            }
            llmViewModel.waitingForResponse = false
        }
    }

    private fun sendQueryToRemote() {
        if (llmViewModel.currentMessage.isBlank()) {
            return
        }
        llmViewModel.currentChatList.add(
            LLMItemModel(llmViewModel.currentMessage, "", true)
        )
        chatHelper.sendQueryToLLM(llmViewModel.currentMessage) {
            it.displayText?.let { replyList ->
                llmViewModel.currentChatList.add(
                    LLMItemModel(replyList[0], "", false)
                )
            }
        }
    }

    private fun getChatHistory() {
        chatHelper.getChatHistory(UserInfoUtil.getUserId(), llmViewModel.pageNum, llmViewModel.pageSize
        ) { historyVOS ->
            historyVOS.forEach {
                if (it.message != null) {
                    llmViewModel.currentChatList.add(
                        LLMItemModel(it.message!!, it.gmtCreate ?: "", false)
                    )
                }
            }
            llmViewModel.pageNum++
        }
    }

}