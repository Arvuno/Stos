package com.m4ykey.stos.answer.domain.repository

import androidx.paging.PagingData
import com.m4ykey.stos.answer.domain.model.AnswerComment
import kotlinx.coroutines.flow.Flow

interface AnswerRepository {

    fun getCommentAnswer(id : Int, pageSize : Int, page : Int) : Flow<PagingData<AnswerComment>>

}