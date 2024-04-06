package com.example.appdemo.ui.common

import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentContainerView


@Composable
fun HomeFragmentContainer(
    onUpdate: (Int) -> Unit
) {
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
                onUpdate(fragmentId)
            }
        )
    }
}