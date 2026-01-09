package com.m4ykey.core.views

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ActionIconButton(
    text : StringResource,
    icon : DrawableResource,
    onClick : () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            contentDescription = stringResource(text),
            painter = painterResource(icon)
        )
    }
}