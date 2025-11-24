package com.m4ykey.stos.search.domain.use_case

import androidx.paging.PagingData
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow

class SearchUseCase(
    private val repository: SearchRepository
) {

    fun searchQuestions(
        page : Int = 1,
        pageSize : Int = 20,
        sort : String,
        inTitle : String?,
        tagged : String?
    ) : Flow<PagingData<Question>> {
        return repository.searchQuestions(
            page = page,
            pageSize = pageSize,
            sort = sort,
            inTitle = inTitle,
            tagged = tagged
        )
    }

}