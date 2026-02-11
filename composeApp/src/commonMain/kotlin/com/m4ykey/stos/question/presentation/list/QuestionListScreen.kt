package com.m4ykey.stos.question.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.views.ActionIconButton
import com.m4ykey.core.views.BasePagingList
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.QuestionItem
import com.m4ykey.stos.question.presentation.components.chip.ChipList
import com.m4ykey.stos.question.presentation.list.enums.QuestionSort
import com.m4ykey.stos.question.presentation.list.model.DrawerItem
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.menu
import kmp_stos.composeapp.generated.resources.search
import kmp_stos.composeapp.generated.resources.settings
import kmp_stos.composeapp.generated.resources.sites
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionListScreen(
    viewModel: QuestionListViewModel = koinViewModel(),
    onQuestionClick : (Int) -> Unit,
    onSearch : () -> Unit,
    onUserClick: (Int) -> Unit,
    onSitesClick : () -> Unit,
    onSettingsClick : () -> Unit
) {
    val questions = viewModel.getQuestionsFlow().collectAsLazyPagingItems()
    val viewState by viewModel.questionListState.collectAsState()
    val onAction = viewModel::onAction

    val sort = viewState.sort

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val lazyListState = rememberLazyListState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.listUiEvent.collectLatest { event ->
            when (event) {
                is ListUiEvent.NavigateToQuestion -> onQuestionClick(event.id)
                is ListUiEvent.NavigateToUser -> onUserClick(event.id)
            }
        }
    }

    val showScrollToTopButton by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 20
        }
    }

    val items = remember {
        listOf(
            DrawerItem(
                onClick = { onSitesClick() },
                titleRes = Res.string.sites,
                icon = Res.drawable.sites
            ),
            DrawerItem(
                onClick = { onSettingsClick() },
                titleRes = Res.string.settings,
                icon = Res.drawable.settings
            ),
        )
    }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    var selectedItemIndex by rememberSaveable { mutableStateOf(-1) }

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(item.titleRes)) },
                        selected = index == selectedItemIndex,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                                selectedItemIndex = -1
                            }
                            item.onClick()
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        icon = {
                            Icon(
                                contentDescription = stringResource(item.titleRes),
                                painter = painterResource(item.icon)
                            )
                        }
                    )
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    scrollBehavior = scrollBehavior,
                    title = {},
                    actions = {
                        ActionIconButton(
                            onClick = onSearch,
                            icon = Res.drawable.search,
                            text = Res.string.search
                        )
                    },
                    navigationIcon = {
                        ActionIconButton(
                            onClick = { coroutineScope.launch { drawerState.open() } },
                            icon = Res.drawable.menu,
                            text = Res.string.menu
                        )
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
                onUserClick = onUserClick,
                coroutineScope = coroutineScope
            )
        }
    }
}

@Composable
fun QuestionListContent(
    listState : LazyListState,
    sort : QuestionSort,
    padding : PaddingValues,
    questions : LazyPagingItems<Question>,
    onAction : (QuestionListAction) -> Unit,
    onQuestionClick: (Int) -> Unit,
    availableSorts : List<QuestionSort> = QuestionSort.entries,
    onUserClick : (Int) -> Unit,
    coroutineScope : CoroutineScope
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        ChipList(
            selectedChip = sort,
            onChipSelected = { selectedSort ->
                onAction(QuestionListAction.OnSortClick(selectedSort))
                coroutineScope.launch {
                    listState.animateScrollToItem(0)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            availableSorts = availableSorts
        )
        Spacer(modifier = Modifier.height(8.dp))
        BasePagingList(
            itemContent = { question ->
                QuestionItem(
                    question = question,
                    onQuestionClick = { onQuestionClick(question.questionId) },
                    onUserClick = { onUserClick(question.owner.userId) }
                )
            },
            items = questions,
            modifier = Modifier.fillMaxWidth(),
            listState = listState,
            itemKey = { it.questionId }
        )
    }
}