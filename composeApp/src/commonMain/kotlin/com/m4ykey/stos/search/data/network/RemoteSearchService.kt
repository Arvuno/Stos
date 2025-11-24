package com.m4ykey.stos.search.data.network

import com.m4ykey.stos.core.Filters.QUESTION_FILTER
import com.m4ykey.stos.core.model.Items
import com.m4ykey.stos.question.data.network.model.QuestionDto

interface RemoteSearchService {

    suspend fun searchQuestions(
        filter : String = QUESTION_FILTER,
        page : Int,
        pageSize : Int,
        sort : String,
        tagged : String?,
        inTitle : String?
    ) : Items<QuestionDto>

}