package com.m4ykey.stos.question.presentation.detail

interface DetailUiEvent {
    data class TagClick(val tag : String) : DetailUiEvent
    data class RelatedClick(val id : Int) : DetailUiEvent
    data class CommentClick(val id : Int) : DetailUiEvent
    data class UserClick(val id : Int) : DetailUiEvent
}