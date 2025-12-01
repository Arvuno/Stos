package com.m4ykey.stos.search.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.core.views.BasePagingList
import com.m4ykey.stos.question.presentation.components.QuestionItem
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back
import kmp_stos.composeapp.generated.resources.search
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchListScreen(
    tag : String,
    inTitle : String,
    onBack : (() -> Unit),
    viewModel: SearchViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit
) {
    val questions = viewModel.searchResults.collectAsLazyPagingItems()

    val initialSearchText = buildString {
        if (inTitle.isNotEmpty()) {
            append(inTitle)
        }
        if (tag.isNotEmpty()) {
            if (inTitle.isNotEmpty()) append(" ")
            append(tag)
        }
    }

    var searchText by rememberSaveable { mutableStateOf(initialSearchText) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()

    LaunchedEffect(inTitle, tag) {
        if (initialSearchText.isNotEmpty()) {
            viewModel.searchQuestion(inTitle, tag)
        }
    }

    LaunchedEffect(viewModel) {
        viewModel.listUiEvent.collectLatest { event ->
            when (event) {
                is ListUiEvent.OnQuestionClick -> onQuestionClick(event.id)
                is ListUiEvent.ChangeSort -> viewModel.updateSort(event.sort)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = { Text(text = stringResource(Res.string.search)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            contentDescription = stringResource(Res.string.back),
                            painter = painterResource(Res.drawable.arrow_left)
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            SearchBox(
                value = searchText,
                onSearch = { viewModel.searchQuestion(searchText, "") },
                onValueChange = { searchText = it },
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            BasePagingList(
                itemContent = { question ->
                    QuestionItem(
                        question = question,
                        onQuestionClick = {
                            onQuestionClick(question.questionId)
                        }
                    )
                },
                items = questions,
                listState = listState
            )
        }
    }
}