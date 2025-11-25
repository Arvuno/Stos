package com.m4ykey.stos.question.presentation.comment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.m4ykey.stos.question.domain.model.QuestionComment
import com.m4ykey.stos.question.domain.use_case.QuestionUseCase
import kotlinx.coroutines.flow.Flow

class QuestionCommentViewModel(
    private val useCase: QuestionUseCase
) : ViewModel() {

    fun getQuestionComment(id : Int) : Flow<PagingData<QuestionComment>> {
        return useCase.getQuestionComment(id = id)
            .cachedIn(viewModelScope)
    }

}