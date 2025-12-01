package com.m4ykey.stos.question.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClosedDetailsDto(
    val description : String? = null,
    @SerialName("original_questions") val originalQuestions : String? = null,
    val reason : String? = null
)
