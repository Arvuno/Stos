package com.m4ykey.stos.answer.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.core.views.BasePagingList
import com.m4ykey.stos.core.views.TextMarkdown
import com.m4ykey.stos.owner.presentation.components.OwnerCard
import com.m4ykey.stos.question.presentation.components.badge.BadgeRow
import com.m4ykey.stos.question.presentation.components.formatCreationDate
import com.m4ykey.stos.question.presentation.components.formatReputation

@Composable
fun AnswerCommentItem(
    comment: AnswerComment
) {
    val formattedDate = formatCreationDate(comment.creationDate.toLong())

    Column(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OwnerCard(owner = comment.owner)
            TextMarkdown(text = comment.owner.displayName)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                fontSize = 13.sp,
                text = formatReputation(comment.owner.reputation)
            )
            BadgeRow(comment.owner.badgeCounts)
        }
        TextMarkdown(text = comment.bodyMarkdown)
        Text(
            text = formattedDate,
            fontSize = 12.sp
        )
    }
}

@Composable
fun CommentSection(
    comments : LazyPagingItems<AnswerComment>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, top = 8.dp)
    ) {
        comments.itemSnapshotList.items.forEach { item ->
            AnswerCommentItem(comment = item)
        }

        if (comments.loadState.append is LoadState.Loading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}