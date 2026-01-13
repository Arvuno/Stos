package com.m4ykey.stos.sites.data.network.service

import com.m4ykey.core.Filters.SITES_FILTER
import com.m4ykey.core.model.Items
import com.m4ykey.stos.sites.data.network.dto.SitesDto

interface RemoteSitesService {

    suspend fun getSites(
        page : Int,
        pageSize : Int,
        filter : String = SITES_FILTER
    ) : Items<SitesDto>

}