package com.m4ykey.stos.question.presentation.list

import com.m4ykey.stos.question.presentation.list.enums.QuestionSort

interface ListUiEvent {
    data class ChangeSort(val sort: QuestionSort) : ListUiEvent
    data class NavigateToQuestion(val id: Int) : ListUiEvent
    data class TagClick(val tag : String) : ListUiEvent
    data class NavigateToSearch(val inTitle : String, val tag : String = "") : ListUiEvent
    data class NavigateToUser(val id : Int) : ListUiEvent
}