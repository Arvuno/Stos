@file:OptIn(ExperimentalMaterial3Api::class)

package com.m4ykey.core.views

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppScaffold(
    modifier : Modifier = Modifier,
    title : StringResource? = null,
    content : @Composable (PaddingValues) -> Unit,
    actions : @Composable RowScope.() -> Unit = {},
    navigation : @Composable () -> Unit = {},
    scrollBehavior : TopAppBarScrollBehavior? = null
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { title?.let { Text(text = stringResource(it)) } },
                navigationIcon = { navigation() },
                actions = { actions() }
            )
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}