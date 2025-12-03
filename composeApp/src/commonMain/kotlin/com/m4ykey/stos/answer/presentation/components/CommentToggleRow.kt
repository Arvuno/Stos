package com.m4ykey.stos.answer.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.comments
import kmp_stos.composeapp.generated.resources.keyboard_arrow_down
import kmp_stos.composeapp.generated.resources.keyboard_arrow_up
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CommentToggleRow(
    isExpanded : Boolean,
    commentCount : Int,
    onClick : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${stringResource(Res.string.comments)}: $commentCount",
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(
                if (isExpanded) Res.drawable.keyboard_arrow_up else Res.drawable.keyboard_arrow_down
            ),
            contentDescription = null
        )
    }
}