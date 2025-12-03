package com.m4ykey.stos.answer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.m4ykey.stos.answer.data.network.service.RemoteAnswerService
import com.m4ykey.stos.answer.data.paging.AnswerCommentPaging
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.answer.domain.repository.AnswerRepository
import com.m4ykey.stos.core.paging.pagingConfig
import kotlinx.coroutines.flow.Flow

class RemoteAnswerRepository(
    private val service : RemoteAnswerService
) : AnswerRepository {

    override fun getCommentAnswer(
        id: Int,
        pageSize: Int,
        page: Int
    ): Flow<PagingData<AnswerComment>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                AnswerCommentPaging(id = id, service = service)
            }
        ).flow
    }

}