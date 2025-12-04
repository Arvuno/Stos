package com.m4ykey.stos.answer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.answer.domain.use_case.AnswerUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest

@OptIn(ExperimentalCoroutinesApi::class)
class AnswerViewModel(
    private val useCase: AnswerUseCase
) : ViewModel() {

    fun getCommentsFlow(id : Int) : Flow<PagingData<AnswerComment>> {
        return useCase(id = id).cachedIn(viewModelScope)
    }

}