package com.m4ykey.stos.answer.data.network.dto

import com.m4ykey.stos.question.data.network.dto.QuestionOwnerDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnswerCommentDto(
    val body: String? = null,
    @SerialName("body_markdown") val bodyMarkdown: String? = null,
    @SerialName("comment_id") val commentId: Int? = null,
    @SerialName("creation_date") val creationDate: Int? = null,
    val owner: QuestionOwnerDto? = null
)