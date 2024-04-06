package com.example.appdemo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.commit
import com.example.appdemo.activity.viewmodel.HomeViewModel
import com.example.appdemo.ui.common.BottomBar
import com.example.appdemo.ui.common.HomeFragmentContainer
import com.example.appdemo.util.StatusBarUtil
import com.example.router.annotation.Router


@Router(url = "native://home", description = "首页")
class HomeActivity: FragmentActivity() {

    private var flutterFragment: Fragment? = null

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.allowLayoutBehindStatusBar()
        StatusBarUtil.changeStatusBarDarkMode()
        StatusBarUtil.wantToGetStatusBarHeight(this)
        setContent{
            HomePage()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        viewModel.needRefresh = true
    }

    @Preview
    @Composable
    fun HomePage() {
        Scaffold(
            bottomBar = { BottomBar(viewModel) }
        ) {
            padding -> HomeCommonContainer(padding)
        }
    }

    @Composable
    fun HomeCommonContainer(paddingValues: PaddingValues) {
        HomeFragmentContainer { fragmentId ->
            if (viewModel.firstLoad) {
                supportFragmentManager.commit {
                    viewModel.tabItems.forEach { item ->
                        val fragment = getFragmentById(item.id)
                        add(fragmentId, fragment, item.id)
                        Log.d("HomeActivity", "$fragment with id $fragmentId and tag ${item.id} is added!!")
                        if (item.id != viewModel.selectedTabModel.id) {
                            hide(fragment)
                        }
                    }
                    Log.d("HomeActivity", "fragment first load!!")
                }
                viewModel.firstLoad = false
            } else {
                supportFragmentManager.commit {
                    Log.d("HomeActivity", "fragment changed, current: ${viewModel.selectedTabModel.id}")
                    viewModel.tabItems.forEach { item ->
                        val fragment = getFragmentById(item.id)
                        if (fragment.tag == viewModel.selectedTabModel.id) {
                            show(fragment)
                        } else {
                            hide(fragment)
                        }
                    }
                }
            }
        }
    }

    private fun getFragmentById(id: String): Fragment {
        return when (id) {
            "home" -> viewModel.cachedFragment["home"]!!
            "content" -> viewModel.cachedFragment["content"]!!
            "mine" -> viewModel.cachedFragment["mine"]!!
            else -> viewModel.cachedFragment["home"]!!
        }
    }

}

