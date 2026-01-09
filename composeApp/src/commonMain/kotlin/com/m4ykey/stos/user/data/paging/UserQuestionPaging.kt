package com.m4ykey.stos.user.data.paging

import com.m4ykey.core.paging.BasePagingSource
import com.m4ykey.stos.core.model.toDomain
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.user.data.network.service.RemoteUserService

class UserQuestionPaging(
    private val service : RemoteUserService,
    private val id : Int
) : BasePagingSource<Question>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<Question>> {
        return safeApi {
            service.getUserQuestions(page = page, pageSize = pageSize, id = id)
        }.run {
            when (this) {
                is ApiResult.Success -> {
                    val questions = data.items.map { it.toDomain() }
                    Result.success(questions)
                }
                is ApiResult.Failure -> Result.failure(exception)
            }
        }
    }
}