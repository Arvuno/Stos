package com.m4ykey.stos.question.presentation.related

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionRelatedViewModel(
    private val useCase: QuestionUseCase
) : ViewModel() {

    private val _questionListState = MutableStateFlow(RelatedQuestionState())
    val questionListState = _questionListState.asStateFlow()

    fun getRelatedQuestion(id : Int) : Flow<PagingData<Question>> {
        return _questionListState
            .map { it.sort }
            .distinctUntilChanged()
            .flatMapLatest { sort ->
                useCase.getRelatedQuestions(
                    id = id,
                    sort = sort.name
                )
            }
            .cachedIn(viewModelScope)
    }
}