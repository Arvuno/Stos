@file:OptIn(ExperimentalMaterial3Api::class)

package com.m4ykey.stos.user.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.stos.core.network.openBrowser
import com.m4ykey.stos.core.views.ActionIconButton
import com.m4ykey.stos.core.views.AppScaffold
import com.m4ykey.stos.core.views.TextMarkdown
import com.m4ykey.stos.question.domain.model.Question
import com.m4ykey.stos.question.presentation.components.ErrorCard
import com.m4ykey.stos.question.presentation.components.badge.BadgeRow
import com.m4ykey.stos.question.presentation.components.formatReputation
import com.m4ykey.stos.user.domain.model.User
import com.m4ykey.stos.user.presentation.components.UserCard
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back
import kmp_stos.composeapp.generated.resources.link
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserScreen(
    id : Int,
    onBack : () -> Unit,
    viewModel: UserViewModel = koinViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val state = rememberLazyListState()

    val questions = viewModel.getUserQuestion(id).collectAsLazyPagingItems()

    LaunchedEffect(viewModel) {
        viewModel.getUser(id)
    }

    val userUiState = viewModel.userUiState.collectAsState()
    val uiState = userUiState.value

    AppScaffold(
        scrollBehavior = scrollBehavior,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        content = { padding ->
            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.error != null -> {
                        ErrorCard(
                            modifier = Modifier.align(Alignment.Center),
                            error = uiState.error
                        )
                    }
                    uiState.loading == true -> {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    uiState.user != null -> {
                        UserContent(
                            paddingValues = padding,
                            state = state,
                            user = uiState.user,
                            questions = questions
                        )
                    }
                }
            }
        },
        navigation = {
            ActionIconButton(
                onClick = onBack,
                icon = Res.drawable.arrow_left,
                text = Res.string.back
            )
        },
        actions = {
            val link = uiState.user?.link
            if (!link.isNullOrEmpty()) {
                ActionIconButton(
                    icon = Res.drawable.link,
                    text = Res.string.link,
                    onClick = { openBrowser(link) }
                )
            }
        }
    )
}

@Composable
fun UserContent(
    paddingValues: PaddingValues,
    state : LazyListState,
    user: User,
    questions : LazyPagingItems<Question>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = state,
        modifier = Modifier
            .padding(paddingValues)
            .padding(vertical = 5.dp)
    ) {
        item {
            UserCardContent(user = user)
        }
    }
}

@Composable
fun UserCardContent(
    user : User
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserCard(
            user = user,
            size = 75.dp
        )
        Spacer(modifier = Modifier.height(5.dp))
        TextMarkdown(
            text = user.displayName,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = formatReputation(user.reputation))
        BadgeRow(
            badgeCounts = user.badgeCounts,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}