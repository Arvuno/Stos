package com.m4ykey.stos.sites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.sites.domain.model.Sites
import com.m4ykey.stos.sites.domain.use_case.SitesUseCase
import kotlinx.coroutines.flow.Flow

class SitesViewModel(
    private val useCase: SitesUseCase
) : ViewModel() {

    val sites : Flow<PagingData<Sites>> = useCase.invoke()
        .cachedIn(viewModelScope)

}