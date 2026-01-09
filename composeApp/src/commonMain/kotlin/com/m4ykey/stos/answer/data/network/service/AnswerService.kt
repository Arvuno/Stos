package com.m4ykey.stos.answer.data.network.service

import com.m4ykey.core.model.Items
import com.m4ykey.stos.answer.data.network.dto.AnswerCommentDto
import com.m4ykey.stos.core.network.setParameters
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class AnswerService(
    private val httpClient: HttpClient
) : RemoteAnswerService {

    override suspend fun getCommentAnswer(
        filter: String,
        page: Int,
        pageSize: Int,
        id : Int
    ): Items<AnswerCommentDto> {
        return httpClient.get {
            url {
                appendPathSegments("answers/$id/comments")
                setParameters(
                    "filter" to filter,
                    "page" to page,
                    "pagesize" to pageSize
                )
            }
        }.body()
    }

}