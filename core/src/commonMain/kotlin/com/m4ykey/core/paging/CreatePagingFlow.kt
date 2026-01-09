package com.m4ykey.core.paging

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

fun <T: Any> createPagingFlow(
    dispatcher : CoroutineDispatcher,
    pagingSourceFactory : () -> PagingSource<Int, T>
) : Flow<PagingData<T>> {
    return Pager(
        config = pagingConfig,
        pagingSourceFactory = pagingSourceFactory
    ).flow.flowOn(dispatcher)
}