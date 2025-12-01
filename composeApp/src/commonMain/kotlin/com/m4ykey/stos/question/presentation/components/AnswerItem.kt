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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.stos.core.views.TextMarkdown
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.QuestionOwner
import com.m4ykey.stos.question.presentation.detail.DisplayOwner
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.votes
import org.jetbrains.compose.resources.stringResource

@Composable
fun AnswerItem(
    answer : QuestionAnswer,
    owner : QuestionOwner
) {
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
            DisplayOwner(item = owner)
        }
        Text(
            text = "${answer.upVoteCount} ${stringResource(Res.string.votes)}",
            fontSize = 14.sp
        )
        TextMarkdown(text = answer.bodyMarkdown)
    }
}