package com.m4ykey.stos.search.data.network

import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.core.network.setParameters
import com.m4ykey.stos.question.data.network.dto.QuestionDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments

class SearchService(
    private val client : HttpClient
) : RemoteSearchService {

    override suspend fun searchQuestions(
        filter: String,
        page: Int,
        pageSize: Int,
        sort: String,
        tagged: String?,
        inTitle: String?
    ): Items<QuestionDto> {
        return client.get {
            url {
                appendPathSegments("search")
                setParameters(
                    "filter" to filter,
                    "page" to page,
                    "pagesize" to pageSize,
                    "sort" to sort
                )
                inTitle?.let { title ->
                    parameters.append("intitle", title)
                }
                tagged?.let { tag ->
                    parameters.append("tagged", tag)
                }
            }
        }.body()
    }
}