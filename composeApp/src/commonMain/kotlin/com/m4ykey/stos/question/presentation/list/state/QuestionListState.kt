package com.m4ykey.stos.question.presentation.list.state

import com.m4ykey.stos.core.views.BaseListState
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort

data class QuestionListState(
    override val sort : QuestionSort = QuestionSort.HOT
) : BaseListState