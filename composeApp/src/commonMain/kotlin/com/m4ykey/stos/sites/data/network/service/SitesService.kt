package com.m4ykey.stos.sites.data.network.service

import com.m4ykey.core.model.Items
import com.m4ykey.stos.core.network.setParameters
import com.m4ykey.stos.sites.data.network.dto.SitesDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class SitesService(
    private val httpClient : HttpClient
) : RemoteSitesService {

    override suspend fun getSites(
        page: Int,
        pageSize: Int,
        filter: String
    ): Items<SitesDto> {
        return httpClient.get("sites") {
            url {
                setParameters(
                    "page" to page,
                    "filter" to filter,
                    "pagesize" to pageSize
                )
            }
        }.body()
    }
}