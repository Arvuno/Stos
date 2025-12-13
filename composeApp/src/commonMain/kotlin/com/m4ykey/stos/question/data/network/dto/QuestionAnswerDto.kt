package com.m4ykey.stos.question.data.network.dto

import com.m4ykey.stos.user.data.network.dto.UserDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuestionAnswerDto(
    @SerialName("body_markdown") val bodyMarkdown : String? = null,
    @SerialName("creation_date") val creationDate : Int? = 0,
    val owner : UserDto? = null,
    @SerialName("down_vote_count") val downVoteCount : Int? = 0,
    @SerialName("answer_id") val answerId : Int? = 0,
    @SerialName("up_vote_count") val upVoteCount : Int? = 0,
    @SerialName("is_accepted") val isAccepted : Boolean = false,
    @SerialName("comment_count") val commentCount : Int? = 0
)
