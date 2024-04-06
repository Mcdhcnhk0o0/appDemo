package com.example.appdemo.ui.common

import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetPage(
    sheet: @Composable (scope: CoroutineScope, bottomSheetState: BottomSheetState) -> Unit,
    content: @Composable (scope: CoroutineScope, bottomSheetState: BottomSheetState) -> Unit
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val bottomSheetState = scaffoldState.bottomSheetState
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            sheet(scope = scope, bottomSheetState = bottomSheetState)
        },
        sheetPeekHeight = 0.dp,
        backgroundColor = Color.Transparent
    ) {
        content(scope = scope, bottomSheetState = bottomSheetState)
    }
}