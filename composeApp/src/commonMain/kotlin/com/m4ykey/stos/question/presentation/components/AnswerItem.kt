package com.m4ykey.stos.question.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.markdown.TextMarkdown
import com.m4ykey.stos.answer.domain.model.AnswerComment
import com.m4ykey.stos.answer.presentation.components.CommentSection
import com.m4ykey.stos.answer.presentation.components.CommentToggleRow
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.presentation.detail.DisplayOwner
import com.m4ykey.stos.user.domain.model.User
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.votes
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnswerItem(
    answer : QuestionAnswer,
    user : User,
    onLoadComments : (id : Int) -> Flow<PagingData<AnswerComment>>,
    onUserClick : (Int) -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    val commentsPaging = onLoadComments(answer.answerId).collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (answer.isAccepted) {
                Card(
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF25C706))
                ) {
                    Icon(
                        contentDescription = null,
                        imageVector = Icons.Default.Check,
                        tint = Color.White,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }
            DisplayOwner(
                item = user,
                onUserClick = onUserClick
            )
        }
        Text(
            text = "${answer.upVoteCount} ${stringResource(Res.string.votes)}",
            fontSize = 14.sp
        )
        TextMarkdown(
            alignment = Alignment.TopStart,
            text = answer.bodyMarkdown
        )
        if (answer.commentCount > 0) {
            CommentToggleRow(
                isExpanded = isExpanded,
                commentCount = answer.commentCount,
                onClick = { isExpanded = !isExpanded }
            )
        }

        if (isExpanded) {
            CommentSection(
                commentsPaging,
                onUserClick = onUserClick
            )
        }
    }
}