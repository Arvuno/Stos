package com.m4ykey.stos.question.domain.model

data class QuestionComment(
    val body : String,
    val bodyMarkdown : String,
    val creationDate : Int,
    val owner : QuestionOwner
)
