package com.m4ykey.stos.core.paging

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.PagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

fun <T: Any> createPagingFlow(
    pagingSourceFactory : () -> PagingSource<Int, T>,
    dispatcher: CoroutineDispatcher
) : Flow<PagingData<T>> {
    return Pager(
        config = pagingConfig,
        pagingSourceFactory = pagingSourceFactory
    ).flow.flowOn(dispatcher)
}