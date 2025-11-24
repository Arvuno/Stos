package com.m4ykey.stos.search.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.m4ykey.stos.core.paging.pagingConfig
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.data.network.RemoteSearchService
import com.m4ykey.stos.search.data.paging.SearchPaging
import com.m4ykey.stos.search.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

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
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                SearchPaging(
                    service = remoteSearchService,
                    sort = sort,
                    inTitle = inTitle,
                    tagged = tagged
                )
            }
        ).flow.flowOn(dispatcherIO)
    }
}