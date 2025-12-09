package com.m4ykey.stos.user.domain.model

import com.m4ykey.stos.question.domain.model.BadgeCounts

data class User(
    val badgeCounts: BadgeCounts,
    val displayName : String,
    val link : String,
    val profileImage : String,
    val reputation : Int,
    val userId : Int
)
