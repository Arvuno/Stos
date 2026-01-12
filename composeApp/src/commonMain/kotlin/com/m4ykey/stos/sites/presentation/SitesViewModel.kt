@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.sites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.sites.domain.model.Sites
import com.m4ykey.stos.sites.domain.use_case.SitesUseCase
import com.m4ykey.stos.sites.presentation.components.SiteManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class SitesViewModel(
    private val useCase: SitesUseCase,
    private val siteManager: SiteManager
) : ViewModel() {

    val selectedSite = siteManager.selectedSite

    val sites : Flow<PagingData<Sites>> = siteManager.selectedSite
        .flatMapLatest { _ ->
            useCase.invoke()
        }.cachedIn(viewModelScope)

    fun selectSite(site : Sites) {
        siteManager.updateSite(site.apiSiteParameter)
    }

}