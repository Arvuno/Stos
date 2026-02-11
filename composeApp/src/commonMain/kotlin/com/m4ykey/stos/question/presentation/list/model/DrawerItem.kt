package com.m4ykey.stos.question.presentation.list.model

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class DrawerItem(
    val titleRes : StringResource,
    val onClick : () -> Unit,
    val icon : DrawableResource
)
