package com.m4ykey.stos.question.data.network.service

import com.m4ykey.core.model.Items
import com.m4ykey.stos.core.network.setParameters
import com.m4ykey.stos.question.data.network.dto.QuestionAnswerDto
import com.m4ykey.stos.question.data.network.dto.QuestionCommentDto
import com.m4ykey.stos.question.data.network.dto.QuestionDetailDto
import com.m4ykey.stos.question.data.network.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class QuestionService(
    private val client : HttpClient
) : RemoteQuestionService {

    override suspend fun getQuestionsByTag(
        filter: String,
        page: Int,
        pageSize: Int,
        sort: String,
        tagged: String
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("questions")
                setParameters(
                    "filter" to filter,
                    "page" to page,
                    "pagesize" to pageSize,
                    "sort" to sort,
                    "tagged" to tagged
                )
            }
        }.body()
    }

    override suspend fun getRelatedQuestions(
        id: Int,
        filter: String,
        page: Int,
        pageSize: Int,
        sort : String
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("questions/$id/related")
                setParameters(
                    "filter" to filter,
                    "page" to page,
                    "pagesize" to pageSize,
                    "sort" to sort
                )
            }
        }.body()
    }

    override suspend fun getQuestionComments(
        id: Int,
        filter: String,
        page: Int,
        pageSize: Int
    ): Items<QuestionCommentDto> {
        return client.get {
            url {
                appendPathSegments("questions/$id/comments")
                setParameters(
                    "filter" to filter,
                    "page" to page,
                    "pagesize" to pageSize
                )
            }
        }.body()
    }

    override suspend fun getQuestionsAnswers(
        filter: String,
        id: Int
    ): Items<QuestionAnswerDto> {
        return client.get {
            url {
                appendPathSegments("questions/$id/answers")
                setParameters("filter" to filter)
            }
        }.body()
    }

    override suspend fun getQuestions(
        page: Int,
        pageSize: Int,
        filter: String,
        sort: String
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("questions")
                setParameters(
                    "page" to page,
                    "pagesize" to pageSize,
                    "filter" to filter,
                    "sort" to sort
                )
            }
        }.body()
    }

    override suspend fun getQuestionById(
        filter: String,
        id: Int
    ): Items<QuestionDetailDto> {
        return client.get {
            url {
                appendPathSegments("questions/$id")
                setParameters("filter" to filter)
            }
        }.body()
    }
}