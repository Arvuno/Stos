package com.m4ykey.stos.sites.data.paging

import com.m4ykey.core.paging.BasePagingSource
import com.m4ykey.stos.core.model.toDomain
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.sites.data.network.service.RemoteSitesService
import com.m4ykey.stos.sites.domain.model.Sites

class SitesPaging(
    private val service : RemoteSitesService
) : BasePagingSource<Sites>() {

    override suspend fun loadData(
        page: Int,
        pageSize: Int
    ): Result<List<Sites>> {
        return safeApi {
            service.getSites(
                page = page,
                pageSize = pageSize
            )
        }.run {
            when (this) {
                is ApiResult.Success -> {
                    val sites = data.items.map { it.toDomain() }
                    Result.success(sites)
                }
                is ApiResult.Failure -> Result.failure(exception)
            }
        }
    }
}