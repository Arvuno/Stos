package com.m4ykey.stos.question.domain.model

import com.m4ykey.stos.user.domain.model.User

data class QuestionDetail(
    val tags : List<String>,
    val owner : User,
    val viewCount : Int,
    val downVoteCount : Int,
    val upVoteCount : Int,
    val answerCount : Int,
    val lastActivityDate : Int,
    val creationDate : Int,
    val questionId : Int,
    val bodyMarkdown : String,
    val link : String,
    val title : String,
    val closedDetails: ClosedDetails,
    val commentCount : Int,
    val lastEditDate : Int,
    val lastEditor : User,
    val closedDate : Int
)
