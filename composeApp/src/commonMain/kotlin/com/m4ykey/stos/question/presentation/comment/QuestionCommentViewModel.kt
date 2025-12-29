@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.question.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.QuestionComment
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class QuestionCommentViewModel(
    useCase: QuestionUseCase
) : ViewModel() {

    private val _id = MutableStateFlow<Int?>(null)

    val questionComment : Flow<PagingData<QuestionComment>> = _id
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapLatest { id ->
            useCase.getQuestionComment(id = id)
                .cachedIn(viewModelScope)
        }

    fun setId(id : Int) {
        _id.value = id
    }

}