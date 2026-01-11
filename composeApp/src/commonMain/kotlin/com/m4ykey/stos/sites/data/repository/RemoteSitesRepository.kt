package com.m4ykey.stos.sites.data.repository

import androidx.paging.PagingData
import com.m4ykey.core.paging.createPagingFlow
import com.m4ykey.stos.sites.data.network.service.RemoteSitesService
import com.m4ykey.stos.sites.data.paging.SitesPaging
import com.m4ykey.stos.sites.domain.model.Sites
import com.m4ykey.stos.sites.domain.repository.SitesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RemoteSitesRepository(
    private val dispatcherIO : CoroutineDispatcher,
    private val service : RemoteSitesService
) : SitesRepository {
    override fun getSites(): Flow<PagingData<Sites>> {
        return createPagingFlow(
            dispatcher = dispatcherIO,
            pagingSourceFactory = {
                SitesPaging(
                    service = service
                )
            }
        )
    }
}