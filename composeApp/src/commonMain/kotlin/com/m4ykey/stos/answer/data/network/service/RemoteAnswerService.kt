package com.m4ykey.stos.answer.data.network.service

import com.m4ykey.core.Filters.ANSWER_COMMENT_FILTER
import com.m4ykey.core.model.Items
import com.m4ykey.stos.answer.data.network.dto.AnswerCommentDto

interface RemoteAnswerService {

    suspend fun getCommentAnswer(
        filter : String = ANSWER_COMMENT_FILTER,
        page : Int,
        pageSize : Int,
        id : Int
    ) : Items<AnswerCommentDto>

}