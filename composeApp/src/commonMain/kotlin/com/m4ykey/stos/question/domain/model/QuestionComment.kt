package com.m4ykey.stos.question.domain.model

import com.m4ykey.stos.core.model.Id
import com.m4ykey.stos.user.domain.model.User

data class QuestionComment(
    val body : String,
    val bodyMarkdown : String,
    val creationDate : Int,
    val owner : User,
    val commentId : Int
) : Id {
    override val id : Any = commentId
}
