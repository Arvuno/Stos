package com.m4ykey.stos

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.m4ykey.stos.app.App
import com.m4ykey.stos.di.initModules
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

fun main() {
    initModules()
    application {
        Window(
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