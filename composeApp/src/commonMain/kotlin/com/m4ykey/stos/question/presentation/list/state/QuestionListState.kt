package com.m4ykey.stos.question.presentation.list.state

import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.list.enums.QuestionOrder
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort

data class QuestionListState(
    val currentPage : Int = 1,
    val questions : List<Question> = emptyList(),
    val sort : QuestionSort = QuestionSort.HOT,
    val order : QuestionOrder = QuestionOrder.DESC
)