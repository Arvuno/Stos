@file:OptIn(ExperimentalCoroutinesApi::class)

package com.m4ykey.stos.question.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m4ykey.stos.core.network.handleApiResult
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import com.m4ykey.stos.sites.data.helpers.SiteManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuestionDetailViewModel(
    private val useCase: QuestionUseCase,
    private val siteManager: SiteManager
) : ViewModel() {

    private val _detailUiEvent = MutableSharedFlow<DetailUiEvent>()
    val detailUiEvent = _detailUiEvent.asSharedFlow()

    private val _currentId = MutableStateFlow<Int?>(null)

    val questionAnswerState : StateFlow<QuestionAnswerState> = combine(
        _currentId.filterNotNull(),
        siteManager.selectedSite
    ) { id, _ -> id }
        .flatMapLatest { id ->
            useCase.getQuestionsAnswer(id)
                .map { result ->
                    var state = QuestionAnswerState()
                    handleApiResult(
                        result = result,
                        success = { data -> state = QuestionAnswerState(answer = data, loading = false) },
                        failure = { msg -> state = QuestionAnswerState(errorMessage = msg, loading = false) }
                    )
                    state
                }
                .onStart { emit(QuestionAnswerState(loading = true)) }
                .catch { emit(QuestionAnswerState(errorMessage = it.message, loading = false)) }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = QuestionAnswerState(loading = true),
            started = SharingStarted.Lazily
        )

    val questionDetailState : StateFlow<QuestionDetailState> = combine(
        _currentId.filterNotNull(),
        siteManager.selectedSite
    ) { id, _ -> id }
        .flatMapLatest { id ->
            useCase.getQuestionById(id)
                .map { result ->
                    var state = QuestionDetailState()
                    handleApiResult(
                        result = result,
                        success = { data -> state = QuestionDetailState(question = data, loading = false) },
                        failure = { msg -> state = QuestionDetailState(errorMessage = msg, loading = false) }
                    )
                    state
                }
                .onStart { emit(QuestionDetailState(loading = true)) }
                .catch { emit(QuestionDetailState(errorMessage = it.message, loading = false)) }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = QuestionDetailState(loading = true),
            started = SharingStarted.Lazily
        )

    fun onAction(action: QuestionDetailAction) {
        viewModelScope.launch {
            when (action) {
                is QuestionDetailAction.OnTagClick -> {
                    _detailUiEvent.emit(DetailUiEvent.TagClick(action.tag))
                }
                is QuestionDetailAction.OnRelatedClick -> {
                    _detailUiEvent.emit(DetailUiEvent.RelatedClick(action.id))
                }
                is QuestionDetailAction.OnCommentClick -> {
                    _detailUiEvent.emit(DetailUiEvent.CommentClick(action.id))
                }
                is QuestionDetailAction.OnUserClick -> {
                    _detailUiEvent.emit(DetailUiEvent.UserClick(action.id))
                }
            }
        }
    }

    fun loadQuestions(id : Int) {
        if (_currentId.value == id) return
        _currentId.value = id
    }
}