package com.m4ykey.stos.answer.data.repository

import androidx.paging.PagingData
import com.m4ykey.core.paging.createPagingFlow
import com.m4ykey.stos.answer.data.network.service.RemoteAnswerService
import com.m4ykey.stos.answer.data.paging.AnswerCommentPaging
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.answer.domain.repository.AnswerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RemoteAnswerRepository(
    private val service : RemoteAnswerService,
    private val dispatcher: CoroutineDispatcher
) : AnswerRepository {

    override fun getCommentAnswer(
        id: Int,
        pageSize: Int,
        page: Int
    ): Flow<PagingData<AnswerComment>> {
        return createPagingFlow(
            dispatcher = dispatcher,
            pagingSourceFactory = { AnswerCommentPaging(id = id, service = service) }
        )
    }

}