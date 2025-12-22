package com.m4ykey.stos.user.domain.use_case

import androidx.paging.PagingData
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.user.domain.model.User
import com.m4ykey.stos.user.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserUseCase(
    private val repository : UserRepository
) {

    fun getUserQuestions(id : Int) : Flow<PagingData<Question>> {
        return repository.getUserQuestions(id = id)
    }

    suspend fun getUser(id : Int) : Flow<ApiResult<User>> {
        return repository.getUserById(id = id)
    }

}