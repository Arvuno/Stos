package com.m4ykey.stos.user.data.network.service

import com.m4ykey.stos.core.Filters.QUESTION_FILTER
import com.m4ykey.stos.core.Filters.USER_FILTER
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.question.data.network.dto.QuestionDto
import com.m4ykey.stos.user.data.network.dto.UserDto

interface RemoteUserService {

    suspend fun getUserQuestions(
        filter : String = QUESTION_FILTER,
        id : Int,
        page : Int,
        pageSize : Int
    ) : Items<QuestionDto>

    suspend fun getUserById(
        filter : String = USER_FILTER,
        id : Int
    ) : Items<UserDto>

}