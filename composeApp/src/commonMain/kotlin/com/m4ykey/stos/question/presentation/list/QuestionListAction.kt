package com.m4ykey.stos.question.presentation.list

import com.m4ykey.stos.question.presentation.list.enums.QuestionSort

sealed interface QuestionListAction {
    data class OnSortClick(val sort : QuestionSort) : QuestionListAction
    data class NavigateToQuestion(val id : Int) : QuestionListAction
    data class NavigateToUser(val id : Int) : QuestionListAction
}