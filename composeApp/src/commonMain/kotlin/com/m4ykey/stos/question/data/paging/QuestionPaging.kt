package com.m4ykey.stos.question.data.paging

import com.m4ykey.core.paging.BasePagingSource
import com.m4ykey.stos.core.model.toDomain
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.data.network.service.RemoteQuestionService
import com.m4ykey.stos.question.domain.model.Question

class QuestionPaging(
    private val service : RemoteQuestionService,
    private val sort : String
) : BasePagingSource<Question>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<Question>> {
        return safeApi {
            service.getQuestions(page, pageSize, sort = sort)
        }.run {
            when (this) {
                is ApiResult.Failure -> Result.failure(exception)
                is ApiResult.Success -> {
                    val questions = data.items.map { it.toDomain() }
                    Result.success(questions)
                }
            }
        }
    }
}