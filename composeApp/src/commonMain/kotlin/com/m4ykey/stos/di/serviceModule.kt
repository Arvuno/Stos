package com.m4ykey.stos.di

import com.m4ykey.stos.answer.data.network.service.AnswerService
import com.m4ykey.stos.answer.data.network.service.RemoteAnswerService
import com.m4ykey.stos.question.data.network.service.QuestionService
import com.m4ykey.stos.question.data.network.service.RemoteQuestionService
import com.m4ykey.stos.search.data.network.service.RemoteSearchService
import com.m4ykey.stos.search.data.network.service.SearchService
import org.koin.dsl.module

val serviceModule = module {
    single<RemoteQuestionService> { QuestionService(get()) }
    single<RemoteSearchService> { SearchService(get()) }
    single<RemoteAnswerService> { AnswerService(get()) }
}