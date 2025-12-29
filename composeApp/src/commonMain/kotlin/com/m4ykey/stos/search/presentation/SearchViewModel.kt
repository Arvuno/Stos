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
import com.m4ykey.stos.search.data.model.TagSection
import com.m4ykey.stos.search.data.model.aiTags
import com.m4ykey.stos.search.data.model.cloudTags
import com.m4ykey.stos.search.data.model.databaseTags
import com.m4ykey.stos.search.data.model.frameworksTags
import com.m4ykey.stos.search.data.model.languageTags
import com.m4ykey.stos.search.data.model.mobileTags
import com.m4ykey.stos.search.data.model.testTags
import com.m4ykey.stos.search.data.model.webTags
import com.m4ykey.stos.search.domain.use_case.SearchUseCase
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.ai
import kmp_stos.composeapp.generated.resources.cloud
import kmp_stos.composeapp.generated.resources.database
import kmp_stos.composeapp.generated.resources.framework
import kmp_stos.composeapp.generated.resources.language
import kmp_stos.composeapp.generated.resources.mobile
import kmp_stos.composeapp.generated.resources.test
import kmp_stos.composeapp.generated.resources.web
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class SearchViewModel(
    private val useCase: SearchUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow(SearchQueryState())

    private val _questionListState = MutableStateFlow(QuestionListState())

    val tagSection : List<TagSection> by lazy {
        listOf(
            TagSection(
                title = Res.string.mobile,
                tags = mobileTags.shuffledAndLimited()
            ),
            TagSection(
                title = Res.string.database,
                tags = databaseTags.shuffledAndLimited()
            ),
            TagSection(
                title = Res.string.test,
                tags = testTags.shuffledAndLimited()
            ),
            TagSection(
                title = Res.string.cloud,
                tags = cloudTags.shuffledAndLimited()
            ),
            TagSection(
                title = Res.string.framework,
                tags = frameworksTags.shuffledAndLimited()
            ),
            TagSection(
                title = Res.string.language,
                tags = languageTags.shuffledAndLimited()
            ),
            TagSection(
                title = Res.string.web,
                tags = webTags.shuffledAndLimited()
            ),
            TagSection(
                title = Res.string.ai,
                tags = aiTags.shuffledAndLimited()
            )
        )
    }

    private val _listUiEvent = MutableSharedFlow<ListUiEvent>()
    val listUiEvent = _listUiEvent.asSharedFlow()

    private val _searchFlow = combine(
        _questionListState,
        _searchQuery
    ) { sort, queryState ->
        QueryParameters(
            sort = "activity",
            inTitle = queryState.inTitle,
            tagged = queryState.tag
        )
    }
        .distinctUntilChanged()
        .debounce { params ->
            if (params.inTitle.isEmpty()) 0L else 1000L
        }
        .flatMapLatest { params ->
            useCase.searchQuestions(
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
                    _listUiEvent.emit(ListUiEvent.TagClick(action.tag))
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

    private fun List<String>.shuffledAndLimited() = this.shuffled().take(10)
}