@file:OptIn(ExperimentalMaterial3Api::class)

package com.m4ykey.stos.question.presentation.comment

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.core.views.BasePagingList
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun QuestionCommentListScreen(
    id : Int,
    viewModel: QuestionCommentViewModel = koinViewModel(),
    onBack : () -> Unit
) {
    val comments = viewModel.getQuestionComment(id).collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("") },
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
    ) { innerPadding ->
        BasePagingList(
            modifier = Modifier.padding(innerPadding),
            itemContent = { comment ->
                CommentItem(comment = comment)
            },
            items = comments,
            listState = listState,
            itemKey = { it.commentId }
        )
    }
}