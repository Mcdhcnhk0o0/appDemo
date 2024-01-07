package com.example.appdemo.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.example.appdemo.activity.home.ui.content.ContentFragment
import com.example.appdemo.activity.home.ui.home.HomeFragment
import com.example.appdemo.activity.home.ui.mine.MineFragment
import com.example.appdemo.activity.viewmodel.HomeViewModel
import com.example.appdemo.activity.viewmodel.NavigationItemModel
import com.example.appdemo.flutter.FlutterRootFragment
import com.example.appdemo.util.StatusBarUtil
import com.example.router.annotation.Router
import io.flutter.embedding.android.FlutterFragment


@Router(url = "native://home", description = "首页")
class HomeActivity: FragmentActivity() {

    private var flutterFragment: Fragment? = null

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.allowLayoutBehindStatusBar()
        StatusBarUtil.changeStatusBarDarkMode()
        StatusBarUtil.wantToGetStatusBarHeight(this)
        acquireFlutterFragment()
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
            bottomBar = { BottomBar() }
        ) {
            padding -> ContentFragment(padding)
        }
    }

    @Composable
    fun BottomBar() {
        BottomNavigation(
            backgroundColor = Color.Black 
        ) {
            viewModel.tabItems.forEach { item ->
                val selected = item == viewModel.selectedTabModel
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        viewModel.selectedTabModel = item
                    },
                    label = {
                        Text(item.description, color = Color.White)
                    },
                    icon = {
                        Icon(
                            imageVector = if (selected) item.selectedIcon else item.unselectedIcon,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                )
            }
        }
    }

    @Composable
    fun ContentFragment(paddingValues: PaddingValues) {
        val topPadding = StatusBarUtil.getStatusBarHeight()
        val fragmentId = View.generateViewId()
        Column {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    FragmentContainerView(context).apply {
                        id = fragmentId
                    }
                },
                update = {
                    if (viewModel.firstLoad) {
                        supportFragmentManager.commit {
                            viewModel.tabItems.forEach { item ->
                                val fragment = getFragmentById(item.id)
                                add(fragmentId, fragment, item.id)
                                Log.d("HomeActivity", "${fragment} with id ${fragmentId} and tag ${item.id} is added!!")
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
            )
//            FragmentContainer(
//                    modifier = Modifier.fillMaxSize(),
//                    fragment = Fragment(),
//                    fragmentManager = supportFragmentManager,
//                    create =  {
//                        viewModel.tabItems.forEach {  item ->
//                            val fragment = getFragmentById(item.id)
//                            add(it, fragment, item.id)
//                        }
//                    },
//                    update = {
//                        viewModel.tabItems.forEach {  item ->
//                            val fragment = getFragmentById(item.id)
//                            if (fragment.tag == viewModel.selectedTabModel.id) {
//                                show(fragment)
//                            } else {
//                                hide(fragment)
//                            }
//                        }
//                    }
//                )
//            if (viewModel.selectedTabModel.id != "mine") {
//                Text(
//                    modifier = Modifier
//                        .wrapContentHeight()
//                        .padding(top = topPadding.dp),
//                    text = "Content In ${viewModel.selectedTabModel.description}",
//                    fontSize = 30.sp
//                )
//                FragmentContainer(
//                    modifier = Modifier.fillMaxSize(),
//                    fragment = fragment,
//                    fragmentManager = supportFragmentManager,
//                    commit =  { replace(it, fragment) }
//                )
//            } else {
//                FragmentContainer(
//                    modifier = Modifier.fillMaxSize(),
//                    fragment = flutterFragment!!,
//                    fragmentManager = supportFragmentManager,
//                    commit =  { replace(it, flutterFragment!!) }
//                )
//            }

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

    private fun acquireFlutterFragment(): Fragment {
        if (flutterFragment == null) {
            flutterFragment = FlutterRootFragment
                .withNewEngine().initialRoute("fragment").build<FlutterFragment>()
        }
        return flutterFragment as Fragment
    }

}


@Composable
fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragment: Fragment,
    fragmentManager: FragmentManager,
    create: FragmentTransaction.(containerId: Int) -> Unit,
    update: FragmentTransaction.(containerId: Int) -> Unit
) {
    val containerId by rememberSaveable { mutableStateOf(View.generateViewId()) }
    var initialized by rememberSaveable { mutableStateOf(false) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            FragmentContainerView(context)
                .apply { id = containerId }
        },
        update = { view ->
            if (!initialized) {
                fragmentManager.commit { create(view.id) }
                initialized = true
                Log.d("HomeActivity", "fragment $fragment initialized!!!")
            } else {
                fragmentManager.commit { update(view.id) }
                Log.d("HomeActivity", "fragment changed, now: $fragment")
            }
        }
    )
}
