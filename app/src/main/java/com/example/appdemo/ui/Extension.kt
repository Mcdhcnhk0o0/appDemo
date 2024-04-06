package com.example.appdemo.ui

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput


fun Modifier.onClick(
    onClick: () -> Unit
): Modifier {
    return pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                onClick()
            }
        )
    }
}