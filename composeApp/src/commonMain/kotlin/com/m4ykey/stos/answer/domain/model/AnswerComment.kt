package com.m4ykey.stos.answer.domain.model

import com.m4ykey.core.model.Id
import com.m4ykey.stos.user.domain.model.User

data class AnswerComment(
    val body : String,
    val bodyMarkdown : String,
    val commentId : Int,
    val creationDate : Int,
    val owner : User
) : Id {
    override val id: Any = commentId
}
