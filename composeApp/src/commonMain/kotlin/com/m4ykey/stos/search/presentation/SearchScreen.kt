package com.m4ykey.stos.search.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.question.presentation.components.chip.ChipItem
import com.m4ykey.stos.question.presentation.list.ListUiEvent
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back
import kmp_stos.composeapp.generated.resources.close
import kmp_stos.composeapp.generated.resources.popular_tags
import kmp_stos.composeapp.generated.resources.search
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = koinViewModel(),
    onSearchScreen : (String, String) -> Unit,
    onBack : () -> Unit
) {

    var inTitle by remember { mutableStateOf("") }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.listUiEvent.collectLatest { event ->
            when (event) {
                is ListUiEvent.NavigateToSearch -> onSearchScreen(event.inTitle, event.tag)
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            painter = painterResource(Res.drawable.arrow_left),
                            contentDescription = stringResource(Res.string.back)
                        )
                    }
                },
                title = { Text(stringResource(Res.string.search)) }
            )
        }
    ) { padding ->
        SearchContent(
            padding = padding,
            listState = listState,
            inTitle = inTitle,
            onSearch = {
                viewModel.onAction(SearchListAction.OnSearchClick(inTitle = inTitle, tag = ""))
            },
            onInTitleChange = { inTitle = it },
            onTagClick = { clickedTag ->
                viewModel.onAction(SearchListAction.OnSearchClick(inTitle = inTitle, tag = clickedTag))
            },
            viewModel = viewModel
        )
    }
}

@Composable
fun SearchContent(
    padding : PaddingValues,
    listState : LazyListState,
    inTitle : String,
    onInTitleChange : (String) -> Unit,
    onSearch : () -> Unit,
    onTagClick : (String) -> Unit,
    viewModel: SearchViewModel
) {
    val tags = viewModel.tagSection

    LazyColumn(
        state = listState,
        modifier = Modifier
            .padding(paddingValues = padding)
            .padding(horizontal = 10.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            SearchBox(
                value = inTitle,
                onValueChange = onInTitleChange,
                onSearch = onSearch
            )
        }
        item {
            Text(
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                text = stringResource(Res.string.popular_tags)
            )
        }
        items(
            items = tags,
            key = { section -> section.title.toString() }
        ) { section ->
            TagList(
                onTagClick = onTagClick,
                tags = section.tags,
                title = stringResource(section.title)
            )
        }
    }
}

@Composable
fun TagList(
    title : String,
    tags : List<String>,
    onTagClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 4.dp)
        ) {
            items(
                items = tags,
                key = { it }
            ) { tag ->
                ChipItem(
                    title = tag,
                    selected = false,
                    onSelect = { onTagClick(tag) }
                )
            }
        }
    }
}

@Composable
fun SearchBox(
    modifier : Modifier = Modifier,
    value : String,
    onValueChange : (String) -> Unit,
    onSearch : () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyUp && event.key == Key.Enter) {
                    onSearch()
                    true
                } else {
                    false
                }
            },
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                contentDescription = null,
                painter = painterResource(Res.drawable.search)
            )
        },
        singleLine = true,
        trailingIcon = {
            if (value.isNotEmpty()) {
                IconButton(onClick = { onValueChange("") }) {
                    Icon(
                        contentDescription = null,
                        painter = painterResource(Res.drawable.close)
                    )
                }
            }
        },
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                onSearch()
            }
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        placeholder = { Text(stringResource(Res.string.search) + "..." ) }
    )
}