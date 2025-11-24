package com.m4ykey.stos.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListState
import com.m4ykey.stos.question.presentation.list.QuestionOrder
import com.m4ykey.stos.question.presentation.list.QuestionSort
import com.m4ykey.stos.search.domain.use_case.SearchUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModel(
    private val useCase: SearchUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow(SearchQueryState())

    private val _questionListState = MutableStateFlow(QuestionListState())
    val questionListState = _questionListState.asStateFlow()

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    private val _searchFlow = combine(
        _questionListState,
        _searchQuery
    ) { listState, queryState ->
        QueryParameters(
            sort = "activity",
            inTitle = queryState.inTitle,
            tagged = queryState.tag
        )
    }
        .distinctUntilChanged()
        .debounce(1000L)
        .flatMapLatest { params ->
            useCase.searchQuestions(
                sort = params.sort,
                inTitle = params.inTitle,
                tagged = params.tagged
            )
        }
        .cachedIn(viewModelScope)

    val searchResults : Flow<PagingData<Question>> = _searchFlow

    fun onAction(action: SearchListAction) {
        viewModelScope.launch {
            val event = when (action) {
                is SearchListAction.OnQuestionClick -> ListUiEvent.OnQuestionClick(action.id)
                is SearchListAction.OnTagClick -> ListUiEvent.TagClick(action.tag)
                is SearchListAction.OnSearchClick -> ListUiEvent.NavigateToSearch(action.inTitle, action.tag)
                is SearchListAction.OnSortClick -> ListUiEvent.ChangeSort(action.sort)
                is SearchListAction.OnOrderClick -> ListUiEvent.ChangeOrder(action.order)
            }
            _listUiEvent.emit(event)
        }
    }

    fun updateSort(sort: QuestionSort) {
        _questionListState.update { it.copy(sort = sort) }
    }

    fun updateOrder(order : QuestionOrder) {
        _questionListState.update { it.copy(order = order) }
    }

    fun searchQuestion(inTitle : String, tag : String) {
        _searchQuery.value = SearchQueryState(inTitle = inTitle, tag = tag)
    }
}