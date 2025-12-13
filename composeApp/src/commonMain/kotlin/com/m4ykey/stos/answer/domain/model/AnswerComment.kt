package com.m4ykey.stos.answer.domain.model

import com.m4ykey.stos.user.domain.model.User

data class AnswerComment(
    val body : String,
    val bodyMarkdown : String,
    val commentId : Int,
    val creationDate : Int,
    val owner : User
)
