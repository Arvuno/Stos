package com.m4ykey.stos.answer.domain.use_case

import androidx.paging.PagingData
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.answer.domain.repository.AnswerRepository
import kotlinx.coroutines.flow.Flow

class AnswerUseCase(
    private val repository: AnswerRepository
) {

    operator fun invoke(id : Int, page : Int = 1, pageSize : Int = 20) : Flow<PagingData<AnswerComment>> {
        return repository.getCommentAnswer(id = id, page = page, pageSize = pageSize)
    }

}