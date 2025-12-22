package com.m4ykey.stos.question.presentation.list

interface ListUiEvent {
    data class NavigateToQuestion(val id: Int) : ListUiEvent
    data class TagClick(val tag : String) : ListUiEvent
    data class NavigateToSearch(val inTitle : String, val tag : String = "") : ListUiEvent
    data class NavigateToUser(val id : Int) : ListUiEvent
}