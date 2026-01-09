package com.m4ykey.stos.question.data.repository

import androidx.paging.PagingData
import com.m4ykey.core.paging.createPagingFlow
import com.m4ykey.stos.core.model.toDomain
import com.m4ykey.stos.core.network.ApiResult
import com.m4ykey.stos.core.network.safeApi
import com.m4ykey.stos.question.data.network.service.RemoteQuestionService
import com.m4ykey.stos.question.data.paging.QuestionCommentPaging
import com.m4ykey.stos.question.data.paging.QuestionPaging
import com.m4ykey.stos.question.data.paging.QuestionRelatedPaging
import com.m4ykey.stos.question.data.paging.QuestionTagPaging
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.QuestionComment
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteQuestionRepository(
    private val remoteQuestionService: RemoteQuestionService,
    private val dispatcherIO : CoroutineDispatcher
) : QuestionRepository {

    override fun getQuestionsByTag(
        page: Int,
        pageSize: Int,
        sort: String,
        tagged: String
    ): Flow<PagingData<Question>> {
        return createPagingFlow(
            pagingSourceFactory = {
                QuestionTagPaging(
                    sort = sort,
                    service = remoteQuestionService,
                    tagged = tagged
                )
            },
            dispatcher = dispatcherIO
        )
    }

    override fun getQuestionsComment(
        page: Int,
        pageSize: Int,
        id: Int
    ): Flow<PagingData<QuestionComment>> {
        return createPagingFlow(
            dispatcher = dispatcherIO,
            pagingSourceFactory = { QuestionCommentPaging(id = id, service = remoteQuestionService) }
        )
    }

    override fun getRelatedQuestions(
        page: Int,
        pageSize: Int,
        id: Int,
        sort : String
    ): Flow<PagingData<Question>> {
        return createPagingFlow(
            dispatcher = dispatcherIO,
            pagingSourceFactory = { QuestionRelatedPaging(id = id, service = remoteQuestionService, sort = sort) }
        )
    }

    override fun getQuestionsAnswer(id: Int): Flow<ApiResult<List<QuestionAnswer>>> {
        return flow {
            val result = safeApi { remoteQuestionService.getQuestionsAnswers(id = id) }

            when (result) {
                is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
                is ApiResult.Success -> {
                    val answers = result.data.items.map { it.toDomain() }
                    emit(ApiResult.Success(answers))
                }
            }
        }.flowOn(dispatcherIO)
    }

    override fun getQuestionById(id: Int): Flow<ApiResult<QuestionDetail>> {
        return flow {
            val result = safeApi { remoteQuestionService.getQuestionById(id = id) }

            when (result) {
                is ApiResult.Failure -> emit(ApiResult.Failure(result.exception))
                is ApiResult.Success -> {
                    val questions = result.data.items.map { it.toDomain() }.firstOrNull()
                    if (questions != null) emit(ApiResult.Success(questions))
                }
            }
        }.flowOn(dispatcherIO)
    }

    override fun getQuestions(
        page: Int,
        pageSize: Int,
        sort: String
    ): Flow<PagingData<Question>> {
        return createPagingFlow(
            dispatcher = dispatcherIO,
            pagingSourceFactory = { QuestionPaging(service = remoteQuestionService, sort = sort) }
        )
    }
}