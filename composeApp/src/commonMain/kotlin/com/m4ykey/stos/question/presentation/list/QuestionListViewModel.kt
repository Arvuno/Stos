package com.m4ykey.stos.question.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort
import com.m4ykey.stos.question.presentation.list.state.QuestionListState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class QuestionListViewModel(
    private val useCase: QuestionUseCase
) : ViewModel() {

    private val _questionListState = MutableStateFlow(QuestionListState())
    val questionListState = _questionListState.asStateFlow()

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    private val _questionFlow = _questionListState
        .map { it.sort }
        .distinctUntilChanged()
        .flatMapLatest { sort ->
            useCase.getQuestions(sort = sort.name)
                .cachedIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = PagingData.empty(),
            started = SharingStarted.Lazily
        )

    fun getQuestionsFlow() : Flow<PagingData<Question>> = _questionFlow

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

    fun updateSort(sort : QuestionSort) {
        _questionListState.update { it.copy(sort = sort) }
    }

}