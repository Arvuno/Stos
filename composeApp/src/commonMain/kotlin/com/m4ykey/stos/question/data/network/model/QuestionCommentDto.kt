package com.m4ykey.stos.question.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionCommentDto(
    @SerialName("creation_date") val creationDate: Int? = 0,
    val owner: QuestionOwnerDto? = null,
    val body : String? = null,
    @SerialName("body_markdown") val bodyMarkdown : String? = null,
    @SerialName("comment_id") val commentId : Int? = 0
)