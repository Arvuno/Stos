package com.m4ykey.stos.question.presentation.related

import com.m4ykey.stos.core.views.BaseListState
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort

data class RelatedQuestionState(
    override val sort : QuestionSort = QuestionSort.ACTIVITY
) : BaseListState
