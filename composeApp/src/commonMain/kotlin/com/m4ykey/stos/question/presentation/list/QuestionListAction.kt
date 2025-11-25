package com.m4ykey.stos.question.presentation.list

import com.m4ykey.stos.question.presentation.list.enums.QuestionOrder
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort

sealed interface QuestionListAction {
    data class OnSortClick(val sort : QuestionSort) : QuestionListAction
    data class OnQuestionClick(val id : Int) : QuestionListAction
    data class OnOrderClick(val order : QuestionOrder) : QuestionListAction
}