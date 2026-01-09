package com.m4ykey.core.paging

import androidx.paging.PagingConfig

val pagingConfig = PagingConfig(
    pageSize = 20,
    enablePlaceholders = false,
    prefetchDistance = 2,
    initialLoadSize = 10
)