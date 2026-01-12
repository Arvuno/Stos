package com.m4ykey.stos.sites.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.m4ykey.stos.sites.domain.model.Sites

@Composable
fun SitesItem(
    modifier : Modifier = Modifier,
    item : Sites
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        SubcomposeAsyncImage(
            model = item.iconUrl,
            contentDescription = item.iconUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(32.dp),
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
        Text(
            text = item.name
        )
    }
}
