package com.m4ykey.stos.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort
import com.m4ykey.stos.question.presentation.list.state.QuestionListState
import com.m4ykey.stos.search.data.model.QueryParameters
import com.m4ykey.stos.search.domain.use_case.SearchUseCase
import com.m4ykey.stos.sites.data.helpers.SiteManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModel(
    private val useCase: SearchUseCase,
    private val siteManager: SiteManager
) : ViewModel() {

    private val _searchQuery = MutableStateFlow(SearchQueryState())

    private val _questionListState = MutableStateFlow(QuestionListState())

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    private val _searchFlow = combine(
        _questionListState,
        _searchQuery,
        siteManager.selectedSite
    ) { _, queryState, site ->
        QueryParameters(
            sort = "activity",
            inTitle = queryState.inTitle,
            tagged = queryState.tag,
            site = site
        )
    }
        .distinctUntilChanged()
        .debounce { params ->
            if (params.inTitle.isEmpty()) 0L else 1000L
        }
        .flatMapLatest { params ->
            useCase.invoke(
                sort = params.sort,
                inTitle = params.inTitle,
                tagged = params.tagged
            )
        }.cachedIn(viewModelScope)

    val searchResults : Flow<PagingData<Question>> = _searchFlow

    fun onAction(action: SearchListAction) {
        viewModelScope.launch {
            when (action) {
                is SearchListAction.OnQuestionClick -> {
                    _listUiEvent.emit(ListUiEvent.NavigateToQuestion(action.id))
                }
                is SearchListAction.OnTagClick -> {
                    //_listUiEvent.emit(ListUiEvent.TagClick(action.tag))
                }
                is SearchListAction.OnSearchClick -> {
                    _listUiEvent.emit(ListUiEvent.NavigateToSearch(action.inTitle, action.tag))
                }
                is SearchListAction.OnSortClick -> {
                    updateSort(sort = action.sort)
                }
            }
        }
    }

    fun updateSort(sort: QuestionSort) {
        _questionListState.update { it.copy(sort = sort) }
    }

    fun searchQuestion(inTitle : String, tag : String) {
        _searchQuery.value = SearchQueryState(inTitle = inTitle, tag = tag)
    }
}