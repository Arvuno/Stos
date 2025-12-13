package com.m4ykey.stos.question.presentation.detail

sealed interface QuestionDetailAction {
    data class OnTagClick(val tag : String) : QuestionDetailAction
    data class OnRelatedClick(val id : Int) : QuestionDetailAction
    data class OnCommentClick(val id : Int) : QuestionDetailAction
    data class OnUserClick(val id : Int) : QuestionDetailAction
}