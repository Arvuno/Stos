package com.m4ykey.stos.search.domain.repository

import androidx.paging.PagingData
import com.m4ykey.stos.question.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun searchQuestions(
        page : Int,
        pageSize : Int,
        sort : String,
        tagged : String?,
        inTitle : String?
    ) : Flow<PagingData<Question>>

}