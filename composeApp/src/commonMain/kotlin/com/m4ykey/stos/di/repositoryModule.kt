package com.m4ykey.stos.di

import com.m4ykey.stos.question.data.repository.RemoteQuestionRepository
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import com.m4ykey.stos.search.data.repository.RemoteSearchRepository
import com.m4ykey.stos.search.domain.repository.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<QuestionRepository> { RemoteQuestionRepository(get(), get()) }
    single<SearchRepository> { RemoteSearchRepository(get(), get()) }
}