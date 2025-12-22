package com.m4ykey.stos.question.presentation.related

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.question.presentation.components.BaseQuestionListScreen
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QuestionRelatedScreen(
    onBack : () -> Unit,
    id : Int,
    viewModel: QuestionRelatedViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit
) {

    val questions = viewModel.getRelatedQuestion(id).collectAsLazyPagingItems()
    val viewState by viewModel.questionListState.collectAsState()
    val onAction = viewModel::onAction

    LaunchedEffect(viewModel) {
        viewModel.listUiEvent.collectLatest { event ->
            when (event) {
                is ListUiEvent.NavigateToQuestion -> onQuestionClick(event.id)
            }
        }
    }

    BaseQuestionListScreen(
        title = "",
        questions = questions,
        onQuestionClick = onQuestionClick,
        onBack = onBack,
        viewState = viewState,
        onAction = onAction,
        availableSorts = RELATED_SORT_OPTIONS,
        onUserClick = {}
    )
}