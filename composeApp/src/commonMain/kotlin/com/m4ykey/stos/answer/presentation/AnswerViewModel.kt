@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.answer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.answer.domain.use_case.AnswerUseCase
import com.m4ykey.stos.sites.data.helpers.SiteManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class AnswerViewModel(
    private val useCase: AnswerUseCase,
    private val siteManager: SiteManager
) : ViewModel() {

    private val commentsCache = mutableMapOf<Int, Flow<PagingData<AnswerComment>>>()

    fun getCommentsFlow(id : Int) : Flow<PagingData<AnswerComment>> {
        return commentsCache.getOrPut(id) {
            siteManager.selectedSite
                .flatMapLatest { _ ->
                    useCase(id = id)
                }
                .cachedIn(viewModelScope)
        }
    }
}