package com.m4ykey.stos.user.data.network.service

import com.m4ykey.core.model.Items
import com.m4ykey.stos.core.network.setParameters
import com.m4ykey.stos.question.data.network.dto.QuestionDto
import com.m4ykey.stos.user.data.network.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.appendPathSegments

class UserService(
    private val client : HttpClient
) : RemoteUserService {

    override suspend fun getUserQuestions(
        filter: String,
        id: Int,
        page: Int,
        pageSize: Int
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("users/$id/questions")
                setParameters(
                    "filter" to filter,
                    "page" to page,
                    "pagesize" to pageSize
                )
            }
        }.body()
    }

    override suspend fun getUserById(
        filter: String,
        id: Int
    ): Items<UserDto> {
        return client.get {
            url {
                appendPathSegments("users/$id")
                parameter("filter", filter)
            }
        }.body()
    }
}