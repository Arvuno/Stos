package com.m4ykey.stos.user.data.repository

import androidx.paging.PagingData
import com.m4ykey.core.paging.createPagingFlow
import com.m4ykey.stos.core.model.toDomain
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.user.data.network.service.RemoteUserService
import com.m4ykey.stos.user.data.paging.UserQuestionPaging
import com.m4ykey.stos.user.domain.model.User
import com.m4ykey.stos.user.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteUserRepository(
    private val service: RemoteUserService,
    private val dispatcher: CoroutineDispatcher
) : UserRepository {

    override suspend fun getUserById(id: Int): Flow<ApiResult<User>> {
        return flow {
            val result = safeApi { service.getUserById(id = id) }

            when (result) {
                is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
                is ApiResult.Success -> {
                    val user = result.data.items.map { it.toDomain() }
                    val getUser = user.firstOrNull()

                    if (getUser != null) {
                        emit(ApiResult.Success(getUser))
                    }
                }
            }
        }.flowOn(dispatcher)
    }

    override fun getUserQuestions(id: Int): Flow<PagingData<Question>> {
        return createPagingFlow(
            pagingSourceFactory = { UserQuestionPaging(service = service, id = id) },
            dispatcher = dispatcher
        )
    }
}