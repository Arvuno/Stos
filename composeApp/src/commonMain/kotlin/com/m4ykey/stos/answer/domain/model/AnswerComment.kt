package com.m4ykey.stos.answer.domain.model

import com.m4ykey.stos.question.domain.model.QuestionOwner

data class AnswerComment(
    val body : String,
    val bodyMarkdown : String,
    val commentId : Int,
    val creationDate : Int,
    val owner : QuestionOwner
)
