package com.m4ykey.stos.answer.data.paging

import com.m4ykey.stos.answer.data.network.service.RemoteAnswerService
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.core.model.toDomain
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.core.paging.BasePagingSource

class AnswerCommentPaging(
    private val service : RemoteAnswerService,
    private val id : Int
) : BasePagingSource<AnswerComment>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<AnswerComment>> {
        return safeApi {
            service.getCommentAnswer(
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