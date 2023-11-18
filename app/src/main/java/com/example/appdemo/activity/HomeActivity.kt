package com.example.appdemo.activity

import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.example.appdemo.activity.home.ui.dashboard.DashboardFragment
import com.example.appdemo.activity.home.ui.home.HomeFragment
import com.example.appdemo.activity.home.ui.notifications.NotificationsFragment
import com.example.appdemo.activity.viewmodel.HomeViewModel
import com.example.appdemo.activity.viewmodel.NavigationItemModel
import com.example.appdemo.flutter.FlutterRootFragment
import com.example.appdemo.flutter.FlutterRuntimeUtil
import com.example.appdemo.util.StatusBarUtil
import com.example.router.annotation.Router
import io.flutter.embedding.android.FlutterFragment


@Router(url = "native://home", description = "首页")
class HomeActivity: FragmentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    private val items = listOf<NavigationItemModel>(
        NavigationItemModel(id = "home", description = "首页", selectedIcon = Icons.Default.Home, unselectedIcon = Icons.Default.Face),
        NavigationItemModel(id = "content", description = "内容", selectedIcon = Icons.Default.Favorite, unselectedIcon = Icons.Default.FavoriteBorder),
        NavigationItemModel(id = "mine", description = "我的", selectedIcon = Icons.Default.Person, unselectedIcon = Icons.Default.AccountCircle)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtil.allowLayoutBehindStatusBar()
        StatusBarUtil.changeStatusBarDarkMode()
        StatusBarUtil.wantToGetStatusBarHeight(this)
        setContent{
            HomePage()
        }
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
            items.forEach { item ->
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
        val fragment = getFragment()
        val flutterFragment = FlutterRootFragment
            .withNewEngine().initialRoute("fragment").build<FlutterFragment>()
        Log.d("HomeActivity", "current fragment: $fragment")

        Column {
            if (viewModel.selectedTabModel.id != "mine") {
                Text(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(top = topPadding.dp),
                    text = "Content In ${viewModel.selectedTabModel.description}",
                    fontSize = 30.sp
                )
                FragmentContainer(
                    modifier = Modifier.fillMaxSize(),
                    fragment = fragment,
                    fragmentManager = supportFragmentManager,
                    commit =  { add(it, fragment) }
                )
            } else {
                FragmentContainer(
                    modifier = Modifier.fillMaxSize(),
                    fragment = flutterFragment,
                    fragmentManager = supportFragmentManager,
                    commit =  { replace(it, flutterFragment) }
                )
            }

        }
    }

    private fun getFragment(): Fragment {
        Log.d("HomeActivity", viewModel.selectedTabModel.id)
        return when (viewModel.selectedTabModel.id) {
            "home" -> HomeFragment()
            "content" -> DashboardFragment()
            "mine" -> NotificationsFragment()
            else -> HomeFragment()
        }

    }

}


@Composable
fun FragmentContainer(
    modifier: Modifier = Modifier,
    fragment: Fragment,
    fragmentManager: FragmentManager,
    commit: FragmentTransaction.(containerId: Int) -> Unit
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
                fragmentManager.commit { commit(view.id) }
                initialized = true
                Log.d("HomeActivity", "fragment $fragment initialized!!!")

            } else {
                fragmentManager.beginTransaction().replace(containerId, fragment).commitNowAllowingStateLoss()
                Log.d("HomeActivity", "fragment changed, now: $fragment")
            }
        }
    )
}
