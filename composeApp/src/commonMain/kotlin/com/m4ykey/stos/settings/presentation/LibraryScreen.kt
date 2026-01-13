@file:OptIn(ExperimentalMaterial3Api::class)

package com.m4ykey.stos.settings.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.m4ykey.core.views.ActionIconButton
import com.m4ykey.core.views.AppScaffold
import com.mikepenz.aboutlibraries.Libs
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back

@Composable
fun LibraryScreen(
    onBack : () -> Unit
) {
    AppScaffold(
        navigation = {
            ActionIconButton(
                text = Res.string.back,
                icon = Res.drawable.arrow_left,
                onClick = onBack
            )
        },
        content = { padding ->
            var libs by remember { mutableStateOf<Libs?>(null) }

            LaunchedEffect(Unit) {
                try {
                    val jsonText = Res.readBytes("files/aboutlibraries.json").decodeToString()
                    libs = Libs.Builder().withJson(jsonText).build()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            if (libs != null) {
                LibrariesContainer(
                    libraries = libs,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                )
            }
        }
    )
}