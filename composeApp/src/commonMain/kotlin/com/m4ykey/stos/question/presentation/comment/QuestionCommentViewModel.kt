@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.question.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.QuestionComment
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import com.m4ykey.stos.sites.data.helpers.SiteManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest

class QuestionCommentViewModel(
    private val useCase: QuestionUseCase,
    private val siteManager: SiteManager
) : ViewModel() {

    private val _id = MutableStateFlow<Int?>(null)

    val questionComment : Flow<PagingData<QuestionComment>> = combine(
        _id.filterNotNull().distinctUntilChanged(),
        siteManager.selectedSite
    ) { id, _ -> id }
        .flatMapLatest { id ->
            useCase.getQuestionComment(id = id)
                .cachedIn(viewModelScope)
        }

    fun setId(id : Int) {
        if (_id.value == id) return
        _id.value = id
    }

}