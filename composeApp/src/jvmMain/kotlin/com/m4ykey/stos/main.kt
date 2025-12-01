package com.m4ykey.stos

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.m4ykey.stos.app.App
import com.m4ykey.stos.di.initModules
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

fun main() {
    initModules()
    application {
        val windowState = rememberWindowState(
            width = 1200.dp,
            height = 800.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        )

        Window(
            state = windowState,
            onCloseRequest = ::exitApplication,
            title = "Stos",
            alwaysOnTop = true,
            resizable = true,
            icon = painterResource(Res.drawable.logo)
        ) {
            App()
        }
    }
}