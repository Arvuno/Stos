package com.m4ykey.stos.di

import com.m4ykey.stos.answer.data.repository.RemoteAnswerRepository
import com.m4ykey.stos.answer.domain.repository.AnswerRepository
import com.m4ykey.stos.question.data.repository.RemoteQuestionRepository
import com.m4ykey.stos.question.domain.repository.QuestionRepository
import com.m4ykey.stos.search.data.repository.RemoteSearchRepository
import com.m4ykey.stos.search.domain.repository.SearchRepository
import com.m4ykey.stos.user.data.repository.RemoteUserRepository
import com.m4ykey.stos.user.domain.repository.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<QuestionRepository> { RemoteQuestionRepository(get(), get()) }
    single<SearchRepository> { RemoteSearchRepository(get(), get()) }
    single<AnswerRepository> { RemoteAnswerRepository(get(), get()) }
    single<UserRepository> { RemoteUserRepository(get(), get()) }
}