package com.example.appdemo.ui.page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.appdemo.ui.common.TitleBar
import com.example.appdemo.ui.onClick


@Composable
fun DebugOptionPage() {
    val columnState = rememberLazyListState()
    val debugOption = listOf(
        mapOf(
            "description" to "Debug开关"
        )
    )
    Column {
        TitleBar(title = "调试选项") {

        }
        LazyColumn(
            state = columnState,
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 20.dp)
        ) {
            items(debugOption.size) { index: Int ->
                val option = debugOption[index]
                DebugOptionCell(description = option["description"]!!, onTap = { /*TODO*/ }) {

                }
            }
        }
    }

}


@Composable
fun DebugOptionCell(
    description: String,
    onTap: () -> Unit,
    status: @Composable () -> Unit
) {
    Row(modifier = Modifier.onClick { onTap() }) {
        Text(text = description)
        status()
    }
}