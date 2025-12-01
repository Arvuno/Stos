package com.m4ykey.stos.question.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class BadgeCountsDto(
    val bronze : Int? = 0,
    val silver : Int? = 0,
    val gold : Int? = 0
)