package com.m4ykey.stos.question.domain.model

data class ClosedDetails(
    val description : String,
    val originalQuestion : String,
    val reason : String
) {
    companion object {
        val EMPTY = ClosedDetails(
            description = "",
            reason = "",
            originalQuestion = ""
        )
    }
}
