package com.m4ykey.stos.sites.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.m4ykey.markdown.TextMarkdown
import com.m4ykey.stos.sites.domain.model.Sites

@Composable
fun SitesItem(
    modifier : Modifier = Modifier,
    item : Sites,
    isSelected : Boolean,
    onClick : () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = item.iconUrl,
            contentDescription = item.iconUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp),
            loading = {
                CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
            },
            error = {
                Icon(
                    imageVector = Icons.Outlined.Person,
                    modifier = Modifier.align(Alignment.Center),
                    contentDescription = "",
                    tint = Color.Gray
                )
            }
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextMarkdown(
                text = item.name,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                alignment = Alignment.TopStart
            )
            val audienceUppercase = item.audience.replaceFirstChar { it.uppercase() }
            TextMarkdown(
                text = audienceUppercase,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                alignment = Alignment.TopStart
            )
        }

        if (isSelected) {
            Icon(
                contentDescription = "Selected",
                tint = MaterialTheme.colorScheme.primary,
                imageVector = Icons.Default.Check
            )
        }
    }
}
