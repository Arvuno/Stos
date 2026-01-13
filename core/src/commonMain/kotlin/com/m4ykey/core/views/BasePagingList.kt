package com.m4ykey.core.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.m4ykey.core.model.Id
import kotlinx.coroutines.delay

private val defaultLoading : @Composable () -> Unit = { DefaultLoading() }
private val defaultError : @Composable (Throwable?, onRetry : () -> Unit) -> Unit = { error, retry ->
    DefaultError(error, retry)
}
private val defaultEmpty : @Composable () -> Unit = { DefaultEmpty() }

@Composable
private fun ErrorItem(message : String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodySmall
        )
        TextButton(onClick = onRetry) {
            Text(text = "Retry")
        }
    }
}

@Composable
private fun DefaultEmpty() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimationImage("files/black_cat_animation.json")
            Text(
                text = "No results found",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun DefaultLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DefaultError(error : Throwable?, onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimationImage(jsonPath = "files/black_cat_animation.json")
            Text(
                text = error?.message ?: "Unexpected error occurred",
                modifier = Modifier.padding(16.dp),
                textAlign = TextAlign.Center
            )
            Button(onClick = onRetry) {
                Text("Try Again")
            }
        }
    }
}

@Composable
fun <T: Any> BasePagingList(
    listState : LazyListState,
    items : LazyPagingItems<T>,
    showDivider : Boolean = true,
    modifier: Modifier = Modifier,
    itemContent : @Composable (item : T) -> Unit,
    loadingContent : @Composable () -> Unit = defaultLoading,
    errorContent : @Composable (Throwable?, onRetry : () -> Unit) -> Unit = defaultError,
    emptyContent : @Composable () -> Unit = defaultEmpty,
    contentPadding : PaddingValues = PaddingValues(10.dp),
    itemKey : (T) -> Any = { if (it is Id) it.id else it.hashCode() }
) {

    var showLoading by remember { mutableStateOf(false) }

    val refreshState = items.loadState.refresh
    val isRefreshing = refreshState is LoadState.Loading

    val emptyItems = items.itemCount == 0

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            showLoading = false
            delay(300L)
            showLoading = true
        } else {
            showLoading = false
        }
    }

    when (refreshState) {
        is LoadState.Loading -> {
            if (showLoading || emptyItems) {
                loadingContent()
            }
        }
        is LoadState.Error -> {
            errorContent(refreshState.error) { items.retry() }
        }
        is LoadState.NotLoading -> {
            if (emptyItems) {
                emptyContent()
            } else {
                LazyColumn(
                    modifier = modifier.fillMaxSize(),
                    state = listState,
                    contentPadding = contentPadding
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey { itemKey(it) },
                        contentType = items.itemContentType { "paged_item" }
                    ) { index ->
                        items[index]?.let { item ->
                            itemContent(item)

                            if (showDivider && index < items.itemCount - 1) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 8.dp),
                                    color = MaterialTheme.colorScheme.outlineVariant
                                )
                            }
                        }
                    }

                    item {
                        when (val appendState = items.loadState.append) {
                            is LoadState.Loading -> {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                            is LoadState.Error -> {
                                ErrorItem(
                                    onRetry = { items.retry() },
                                    message = appendState.error.message ?: "Loading error"
                                )
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}