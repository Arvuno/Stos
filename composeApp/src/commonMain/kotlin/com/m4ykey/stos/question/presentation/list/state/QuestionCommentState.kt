package com.m4ykey.stos.question.presentation.list.state

import com.m4ykey.stos.question.domain.model.QuestionComment

data class QuestionCommentState(
    val currentPage : Int = 1,
    val comments : List<QuestionComment> = emptyList()
)
