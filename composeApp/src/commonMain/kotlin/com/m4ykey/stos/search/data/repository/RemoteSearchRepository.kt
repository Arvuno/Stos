package com.m4ykey.stos.search.data.repository

import androidx.paging.PagingData
import com.m4ykey.core.paging.createPagingFlow
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.data.network.service.RemoteSearchService
import com.m4ykey.stos.search.data.paging.SearchPaging
import com.m4ykey.stos.search.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RemoteSearchRepository(
    private val remoteSearchService: RemoteSearchService,
    private val dispatcherIO : CoroutineDispatcher
) : SearchRepository {

    override fun searchQuestions(
        page: Int,
        pageSize: Int,
        sort: String,
        tagged: String?,
        inTitle: String?
    ): Flow<PagingData<Question>> {
        return createPagingFlow(
            dispatcher = dispatcherIO,
            pagingSourceFactory = {
                SearchPaging(
                    service = remoteSearchService,
                    sort = sort,
                    inTitle = inTitle,
                    tagged = tagged
                )
            }
        )
    }
}