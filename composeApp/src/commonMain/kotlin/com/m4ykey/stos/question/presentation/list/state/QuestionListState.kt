package com.m4ykey.stos.question.presentation.list.state

import com.m4ykey.stos.question.presentation.list.enums.QuestionSort

data class QuestionListState(
    val sort : QuestionSort = QuestionSort.HOT
)