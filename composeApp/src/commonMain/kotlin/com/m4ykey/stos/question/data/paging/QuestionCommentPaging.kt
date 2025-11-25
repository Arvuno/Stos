package com.m4ykey.stos.question.data.paging

import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.core.paging.BasePagingSource
import com.m4ykey.stos.question.data.mapper.toDomain
import com.m4ykey.stos.question.data.network.RemoteQuestionService
import com.m4ykey.stos.question.domain.model.QuestionComment

class QuestionCommentPaging(
    private val service : RemoteQuestionService,
    private val id : Int
) : BasePagingSource<QuestionComment>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<QuestionComment>> {
        return safeApi {
            service.getQuestionComments(
                page = page,
                pageSize = pageSize,
                id = id
            )
        }.run {
            when (this) {
                is ApiResult.Failure -> Result.failure(exception)
                is ApiResult.Success -> {
                    val comments = data.items.map { it.toDomain() }
                    Result.success(comments)
                }
            }
        }
    }
}