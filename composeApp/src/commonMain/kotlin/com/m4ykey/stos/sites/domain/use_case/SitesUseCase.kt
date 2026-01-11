package com.m4ykey.stos.sites.domain.use_case

import androidx.paging.PagingData
import com.m4ykey.stos.sites.domain.model.Sites
import com.m4ykey.stos.sites.domain.repository.SitesRepository
import kotlinx.coroutines.flow.Flow

class SitesUseCase(
    private val repository: SitesRepository
) {

    fun invoke() : Flow<PagingData<Sites>> {
        return repository.getSites()
    }

}