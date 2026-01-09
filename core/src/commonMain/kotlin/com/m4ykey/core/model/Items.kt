package com.m4ykey.core.model

import kotlinx.serialization.Serializable

@Serializable
data class Items<T>(
    val items : List<T>
)