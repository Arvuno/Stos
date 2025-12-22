package com.m4ykey.stos.question.presentation.comment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.core.views.TextMarkdown
import com.m4ykey.stos.question.domain.model.QuestionComment
import com.m4ykey.stos.question.presentation.components.badge.BadgeRow
import com.m4ykey.stos.question.presentation.components.formatCreationDate
import com.m4ykey.stos.question.presentation.components.formatReputation
import com.m4ykey.stos.user.presentation.components.UserCard

@Composable
fun CommentItem(
    comment : QuestionComment
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
            UserCard(user = comment.owner)
            TextMarkdown(
                text = comment.owner.displayName,
                alignment = Alignment.TopStart
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                fontSize = 13.sp,
                text = formatReputation(comment.owner.reputation)
            )
            BadgeRow(comment.owner.badgeCounts)
        }
        TextMarkdown(
            alignment = Alignment.TopStart,
            text = comment.bodyMarkdown
        )
        Text(
            text = formattedDate,
            fontSize = 12.sp
        )
    }
}
