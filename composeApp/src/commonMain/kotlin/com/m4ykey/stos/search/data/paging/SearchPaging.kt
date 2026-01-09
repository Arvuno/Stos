package com.m4ykey.stos.search.data.paging

import com.m4ykey.core.paging.BasePagingSource
import com.m4ykey.stos.core.model.toDomain
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.data.network.service.RemoteSearchService

class SearchPaging(
    private val service : RemoteSearchService,
    private val sort : String,
    private val inTitle : String?,
    private val tagged : String?
) : BasePagingSource<Question>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<Question>> {
        return safeApi {
            service.searchQuestions(
                page = page,
                pageSize = pageSize,
                sort = sort,
                tagged = tagged,
                inTitle = inTitle
            )
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