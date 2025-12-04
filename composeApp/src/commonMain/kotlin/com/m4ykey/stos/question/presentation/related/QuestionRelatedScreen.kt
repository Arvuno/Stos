package com.m4ykey.stos.question.presentation.related

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.question.presentation.components.BaseQuestionListScreen
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import com.m4ykey.stos.question.presentation.list.QuestionListViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QuestionRelatedScreen(
    onBack : () -> Unit,
    id : Int,
    viewModel: QuestionRelatedViewModel = koinViewModel(),
    listViewModel : QuestionListViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit
) {

    val questions = viewModel.getRelatedQuestion(id).collectAsLazyPagingItems()
    val viewState by viewModel.questionListState.collectAsState()
    val onAction = listViewModel::onAction

    LaunchedEffect(listViewModel) {
        listViewModel.listUiEvent.collectLatest { event ->
            when (event) {
                is ListUiEvent.ChangeSort -> listViewModel.updateSort(event.sort)
                is ListUiEvent.OnQuestionClick -> onQuestionClick(event.id)
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
        availableSorts = RELATED_SORT_OPTIONS
    )
}