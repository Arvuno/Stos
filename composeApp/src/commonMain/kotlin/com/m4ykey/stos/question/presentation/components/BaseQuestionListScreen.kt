package com.m4ykey.stos.question.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.compose.LazyPagingItems
import com.m4ykey.core.views.ActionIconButton
import com.m4ykey.stos.core.views.BaseListState
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.list.QuestionListAction
import com.m4ykey.stos.question.presentation.list.QuestionListContent
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseQuestionListScreen(
    title : String? = null,
    onBack : (() -> Unit)? = null,
    questions : LazyPagingItems<Question>,
    viewState : BaseListState,
    onAction : (QuestionListAction) -> Unit,
    onQuestionClick: (Int) -> Unit,
    actions : @Composable RowScope.() -> Unit = {},
    availableSorts : List<QuestionSort> = QuestionSort.entries,
    onUserClick : (Int) -> Unit
) {
    val sort = viewState.sort

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    val showScrollToTopButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 20
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { if (title != null) Text(text = title) },
                navigationIcon = {
                    onBack?.let {
                        ActionIconButton(
                            onClick = it,
                            text = Res.string.back,
                            icon = Res.drawable.arrow_left
                        )
                    }
                },
                actions = {
                    actions()
                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = showScrollToTopButton,
                enter = slideInHorizontally { it },
                exit = slideOutHorizontally { it }
            ) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    },
                    content = {
                        Icon(
                            contentDescription = null,
                            imageVector = Icons.Default.ArrowUpward
                        )
                    }
                )
            }
        }
    ) { padding ->
        QuestionListContent(
            padding = padding,
            listState = lazyListState,
            questions = questions,
            sort = sort,
            onAction = onAction,
            onQuestionClick = onQuestionClick,
            availableSorts = availableSorts,
            onUserClick = onUserClick,
            coroutineScope = coroutineScope
        )
    }
}