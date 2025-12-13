package com.m4ykey.stos.user.presentation

import com.m4ykey.stos.user.domain.model.User

data class UserUiState(
    val error : String? = null,
    val loading : Boolean? = false,
    val user : User? = null
)
