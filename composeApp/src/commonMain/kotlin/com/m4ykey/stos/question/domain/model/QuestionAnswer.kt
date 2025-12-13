package com.m4ykey.stos.question.domain.model

import com.m4ykey.stos.user.domain.model.User

data class QuestionAnswer(
    val bodyMarkdown : String,
    val creationDate : Int,
    val owner : User,
    val downVoteCount : Int,
    val answerId : Int,
    val upVoteCount : Int,
    val isAccepted : Boolean,
    val commentCount : Int
)
