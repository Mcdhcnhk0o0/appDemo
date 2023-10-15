package com.example.appdemo.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.Room.databaseBuilder
import com.example.appdemo.database.ScreenStatusRecordDatabase
import com.example.appdemo.database.entity.ScreenState
import com.example.appdemo.database.entity.ScreenStatusBean
import com.example.appdemo.ui.theme.AppDemoTheme
import com.example.router.annotation.Router
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Router(url = "native://screen_log")
class ScreenLogActivity: ComponentActivity() {

    private val screenRecordList = mutableStateListOf<ScreenStatusBean>()

    private val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.LightGray
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(vertical = 12.dp, horizontal = 12.dp),
                    ) {
                        ScreenLogGroupPage()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getScreenLogAsync()
    }

    @Composable
    fun ScreenLogPage() {
        val scrollState = rememberScrollState()
        val snapshotList = ArrayList(screenRecordList)
        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            snapshotList.forEach {
                LogItem(screenStatusBean = it)
            }
        }
    }

    @Composable
    fun ScreenLogGroupPage() {
        val scrollState = rememberScrollState()
        val snapshotListGroup = groupByDate(ArrayList(screenRecordList))
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            snapshotListGroup.forEach { recordGroup ->
                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .background(
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(12.dp)
                    )
                        .padding(vertical = 20.dp)
                ) {
                    LogGroupItem(records = recordGroup)
                }
            }
        }
    }

    @Composable
    fun LogItem(screenStatusBean: ScreenStatusBean) {
        val recordTime = sdf.format(Date(screenStatusBean.timeStamp))
        val description = when(screenStatusBean.state) {
            ScreenState.SCREEN_ON -> {"屏幕被打开了"}
            ScreenState.SCREEN_OFF -> {"屏幕被关闭了"}
            ScreenState.USER_PRESENT -> {"用户主动解锁了屏幕"}
            else -> {"未知状态"}
        }
        Column(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth()
                .padding(vertical = 4.dp)
                .background(Color.Blue)
        ) {
            Text(text = recordTime, color = Color.LightGray, fontSize = 14.sp)
            Text(modifier = Modifier.padding(start = 20.dp), text = description, color = Color.White, fontSize = 18.sp)
        }
    }

    @Composable
    fun LogGroupItem(records: List<ScreenStatusBean>) {
        Column {
            records.forEach { record ->
                val recordTime = sdf.format(Date(record.timeStamp))
                val description = when(record.state) {
                    ScreenState.SCREEN_ON -> {"屏幕被打开了"}
                    ScreenState.SCREEN_OFF -> {"屏幕被关闭了"}
                    ScreenState.USER_PRESENT -> {"用户主动解锁了屏幕"}
                    else -> {"未知状态"}
                }
                Box(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .wrapContentHeight()
                        .background(
                            color = Color.Blue,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .fillMaxWidth()
                    ) {
                        Text(text = recordTime, color = Color.LightGray, fontSize = 14.sp)
                        Text(modifier = Modifier.padding(start = 20.dp), text = description, color = Color.White, fontSize = 18.sp)
                    }
                }

            }
        }


    }

    private fun getScreenLogAsync() {
        Thread {
            val recordDatabase = databaseBuilder<ScreenStatusRecordDatabase>(
                this,
                ScreenStatusRecordDatabase::class.java, "screen_db"
            ).build()
            screenRecordList.clear()
            screenRecordList.addAll(recordDatabase.screenStatusDao().allRecords)
            recordDatabase.close()
        }.start()
    }

    private fun groupByDate(records: List<ScreenStatusBean>): MutableList<List<ScreenStatusBean>> {
        val recordGroups = mutableListOf<List<ScreenStatusBean>>()
        if (records.isEmpty()) {
            return recordGroups
        }
        records.sortedWith { o1, o2 -> (o1.timeStamp - o2.timeStamp).toInt() }
        val currentGroup = mutableListOf<ScreenStatusBean>()
        var lastDate = Date(records.first().timeStamp)
        var currentDate: Date
        records.forEach { record ->
            if (currentGroup.isEmpty()) {
                currentGroup.add(record)
                return@forEach
            }
            currentDate = Date(record.timeStamp)
            if (currentDate.day == currentDate.day && currentDate.hours == lastDate.hours) {
                currentGroup.add(record)
            } else {
                recordGroups.add(ArrayList(currentGroup))
                Log.d("ScreenLog", "find a group, size: ${currentGroup.size} with day: ${currentDate.day} and hour: ${currentDate.hours}")
                currentGroup.clear()
            }
            lastDate = currentDate
        }
        if (currentGroup.isNotEmpty()) {
            recordGroups.add(ArrayList(currentGroup))
            currentGroup.clear()
        }
        return recordGroups

    }

}