@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.question.presentation.related

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListAction
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionRelatedViewModel(
    private val useCase: QuestionUseCase
) : ViewModel() {

    private val _questionListState = MutableStateFlow(RelatedQuestionState())
    val questionListState = _questionListState.asStateFlow()

    private val _id = MutableStateFlow<Int?>(null)

    fun setId(id : Int) {
        if (_id.value == id) return
        _id.value = id
    }

    val relatedQuestions : Flow<PagingData<Question>> = combine(
        _id.filterNotNull(),
        _questionListState.map { it.sort }.distinctUntilChanged()
    ) { id, sort ->
        id to sort
    }.flatMapLatest { (id, sort) ->
        useCase.getRelatedQuestions(id = id, sort = sort.name)
            .cachedIn(viewModelScope)
    }

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    fun updateSort(sort : QuestionSort) {
        _questionListState.update { it.copy(sort = sort) }
    }

    fun onAction(action: QuestionListAction) {
        viewModelScope.launch {
            when (action) {
                is QuestionListAction.OnSortClick -> {
                    updateSort(action.sort)
                }
                is QuestionListAction.NavigateToQuestion -> {
                    _listUiEvent.emit(ListUiEvent.NavigateToQuestion(action.id))
                }
                is QuestionListAction.NavigateToUser -> {
                    _listUiEvent.emit(ListUiEvent.NavigateToUser(action.id))
                }
            }
        }
    }
}