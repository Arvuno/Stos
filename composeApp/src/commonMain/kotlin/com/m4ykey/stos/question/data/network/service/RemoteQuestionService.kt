package com.m4ykey.stos.question.data.network.service

import com.m4ykey.stos.core.Filters.QUESTION_ANSWER_FILTER
import com.m4ykey.stos.core.Filters.QUESTION_COMMENTS
import com.m4ykey.stos.core.Filters.QUESTION_DETAIL_FILTER
import com.m4ykey.stos.core.Filters.QUESTION_FILTER
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.question.data.network.dto.QuestionAnswerDto
import com.m4ykey.stos.question.data.network.dto.QuestionCommentDto
import com.m4ykey.stos.question.data.network.dto.QuestionDetailDto
import com.m4ykey.stos.question.data.network.dto.QuestionDto

interface RemoteQuestionService {

    suspend fun getQuestionComments(
        id : Int,
        filter : String = QUESTION_COMMENTS,
        page : Int,
        pageSize : Int
    ) : Items<QuestionCommentDto>

    suspend fun getQuestionsAnswers(
        filter : String = QUESTION_ANSWER_FILTER,
        id : Int
    ) : Items<QuestionAnswerDto>

    suspend fun getQuestions(
        page : Int,
        pageSize : Int,
        filter : String = QUESTION_FILTER,
        sort : String
    ) : Items<QuestionDto>

    suspend fun getQuestionById(
        filter : String = QUESTION_DETAIL_FILTER,
        id : Int
    ) : Items<QuestionDetailDto>

    suspend fun getQuestionsByTag(
        filter: String = QUESTION_FILTER,
        page : Int,
        pageSize : Int,
        sort : String,
        tagged : String
    ) : Items<QuestionDto>

}