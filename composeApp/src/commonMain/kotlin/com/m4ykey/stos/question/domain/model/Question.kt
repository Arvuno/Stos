package com.m4ykey.stos.question.domain.model

import com.m4ykey.stos.user.domain.model.User

data class Question(
    val owner : User,
    val viewCount : Int,
    val downVoteCount : Int,
    val upVoteCount : Int,
    val answerCount : Int,
    val creationDate : Int,
    val questionId : Int,
    val title : String
)
