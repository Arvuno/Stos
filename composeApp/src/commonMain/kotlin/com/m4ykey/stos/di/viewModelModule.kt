package com.m4ykey.stos.di

import com.m4ykey.stos.answer.presentation.AnswerViewModel
import com.m4ykey.stos.question.presentation.comment.QuestionCommentViewModel
import com.m4ykey.stos.question.presentation.detail.QuestionDetailViewModel
import com.m4ykey.stos.question.presentation.list.QuestionListViewModel
import com.m4ykey.stos.question.presentation.related.QuestionRelatedViewModel
import com.m4ykey.stos.question.presentation.tag.QuestionTagViewModel
import com.m4ykey.stos.search.presentation.SearchViewModel
import com.m4ykey.stos.sites.presentation.SitesViewModel
import com.m4ykey.stos.user.presentation.UserViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { QuestionListViewModel(get(), get()) }
    viewModel { QuestionDetailViewModel(get()) }
    viewModel { QuestionTagViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { QuestionCommentViewModel(get()) }
    viewModel { AnswerViewModel(get()) }
    viewModel { QuestionRelatedViewModel(get()) }
    viewModel { UserViewModel(get()) }
    viewModel { SitesViewModel(get(), get()) }
}