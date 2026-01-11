package com.m4ykey.stos.sites.domain.repository

import androidx.paging.PagingData
import com.m4ykey.stos.sites.domain.model.Sites
import kotlinx.coroutines.flow.Flow

interface SitesRepository {
    fun getSites() : Flow<PagingData<Sites>>
}