package com.m4ykey.stos.user.presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.m4ykey.stos.user.domain.model.User

@Composable
fun UserCard(
    modifier : Modifier = Modifier,
    user : User,
    size: Dp = 26.dp
) {
    Card(
        shape = CircleShape,
        modifier = modifier.size(size)
    ) {
        SubcomposeAsyncImage(
            model = user.profileImage,
            contentDescription = user.profileImage,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
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
    }
}