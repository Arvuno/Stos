package com.m4ykey.stos.question.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.m4ykey.stos.core.network.openBrowser
import com.m4ykey.stos.core.views.TextMarkdown
import com.m4ykey.stos.owner.presentation.components.OwnerCard
import com.m4ykey.stos.question.data.mapper.toQuestion
import com.m4ykey.stos.question.domain.model.QuestionAnswer
import com.m4ykey.stos.question.domain.model.QuestionDetail
import com.m4ykey.stos.question.domain.model.QuestionOwner
import com.m4ykey.stos.question.presentation.components.AnswerItem
import com.m4ykey.stos.question.presentation.components.ErrorCard
import com.m4ykey.stos.question.presentation.components.QuestionStatsRow
import com.m4ykey.stos.question.presentation.components.badge.BadgeRow
import com.m4ykey.stos.question.presentation.components.chip.ChipItem
import com.m4ykey.stos.question.presentation.components.formatCreationDate
import com.m4ykey.stos.question.presentation.components.formatReputation
import com.mikepenz.markdown.compose.elements.MarkdownText
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.answers
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.asked
import kmp_stos.composeapp.generated.resources.back
import kmp_stos.composeapp.generated.resources.closed
import kmp_stos.composeapp.generated.resources.comment
import kmp_stos.composeapp.generated.resources.comments
import kmp_stos.composeapp.generated.resources.last_edited_by
import kmp_stos.composeapp.generated.resources.link
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionDetailScreen(
    id : Int,
    viewModel : QuestionDetailViewModel = koinViewModel(),
    onBack : () -> Unit,
    onTagClick : (String) -> Unit,
    onCommentClick : (Int) -> Unit
) {
    val detailState by viewModel.questionDetailState.collectAsStateWithLifecycle()
    val answerState by viewModel.questionAnswerState.collectAsStateWithLifecycle()

    val loading = detailState.loading || answerState.loading
    val error = detailState.errorMessage ?: answerState.errorMessage

    val detail = detailState.question
    val answers = answerState.answer

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()

    val onAction = viewModel::onAction

    LaunchedEffect(viewModel) {
        viewModel.detailUiEvent.collectLatest { event ->
            if (event is DetailUiEvent.TagClick) onTagClick(event.tag)
        }
    }

    LaunchedEffect(id) {
        viewModel.loadQuestions(id)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            contentDescription = stringResource(resource = Res.string.back),
                            painter = painterResource(Res.drawable.arrow_left)
                        )
                    }
                },
                actions = {
                    val link = detail?.link
                    if (!link.isNullOrEmpty()) {
                        IconButton(onClick = { openBrowser(link) }) {
                            Icon(
                                imageVector = Icons.Outlined.Public,
                                contentDescription = stringResource(Res.string.link)
                            )
                        }
                    }
                    val commentCount = detail?.commentCount
                    if (commentCount != null && commentCount > 0) {
                        IconButton(onClick = { onCommentClick(id) }) {
                            Icon(
                                contentDescription = stringResource(Res.string.comments),
                                painter = painterResource(Res.drawable.comment)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> {
                    ErrorCard(
                        modifier = Modifier.align(Alignment.Center),
                        error = error
                    )
                }
                detail != null -> {
                    QuestionDetailContent(
                        item = detail,
                        listState = listState,
                        answers = answers,
                        onAction = onAction,
                        paddingValues = padding
                    )
                }
                else -> {
                    Text(
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center),
                        text = "No data"
                    )
                }
            }
        }
    }
}

@Composable
fun ClosedDetailCard(
    reason : String,
    description : String,
    closedDate : Int
) {
    val closedDate = formatCreationDate(closedDate.toLong())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Red,
            contentColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = reason,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            TextMarkdown(
                text = description
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "${stringResource(Res.string.closed)}: $closedDate",
                fontSize = 13.sp
            )
        }
    }
}

@Composable
fun QuestionDetailContent(
    modifier : Modifier = Modifier,
    item : QuestionDetail,
    paddingValues: PaddingValues,
    listState : LazyListState,
    answers : List<QuestionAnswer>,
    onAction: (QuestionDetailAction) -> Unit
) {
    LazyColumn(
        state = listState,
        modifier = modifier.padding(paddingValues = paddingValues).padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            if (item.closedDetails.reason.isNotBlank() || item.closedDetails.description.isNotBlank()) {
                ClosedDetailCard(
                    reason = item.closedDetails.reason,
                    description = item.closedDetails.description,
                    closedDate = item.closedDate
                )
            }
        }
        item {
            TextMarkdown(
                text = item.title,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        item { TextMarkdown(text = item.bodyMarkdown) }

        item {
            if (item.lastEditor.displayName.isNotBlank()) {
                TextMarkdown(
                    fontSize = 15.sp,
                    text = "*${stringResource(Res.string.last_edited_by)}: ${item.lastEditor.displayName}*"
                )
            }
        }

        item {
            TagListWrap(
                tags = item.tags,
                onTagClick = { tag ->
                    onAction(QuestionDetailAction.OnTagClick(tag))
                }
            )
        }
        item {
            QuestionStatsRow(
                iconSize = 16.dp,
                textSize = 15.sp,
                item = item.toQuestion()
            )
        }
        item {
            Text(
                fontSize = 14.sp,
                text = "${stringResource(resource = Res.string.asked)} ${formatCreationDate(item.creationDate.toLong())}"
            )
        }
        item {
            DisplayOwner(item = item.owner)
        }
        item {
            Text(
                text = "${stringResource(resource = Res.string.answers)}: ${item.answerCount}",
                fontSize = 16.sp
            )
        }
        items(
            items = answers,
            key = { it.answerId },
            contentType = { "answer_item" }
        ) { answer ->
            AnswerItem(
                answer = answer,
                owner = answer.owner
            )
            Spacer(modifier = Modifier.height(5.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 5.dp))
        }
    }
}

@Composable
fun DisplayOwner(
    modifier : Modifier = Modifier,
    item: QuestionOwner
) {
    if (item.displayName.isBlank()) return

    Row(modifier = modifier.fillMaxWidth()) {
        OwnerCard(
            owner = item,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            TextMarkdown(text = item.displayName)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    fontSize = 13.sp,
                    text = formatReputation(item.reputation)
                )
                BadgeRow(item.badgeCounts)
            }
        }
    }
}

@Composable
fun TagListWrap(
    tags : List<String>,
    onTagClick : (String) -> Unit
) {
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        tags.forEach { label ->
            ChipItem(
                title = label,
                modifier = Modifier,
                selected = false,
                onSelect = { onTagClick(label) }
            )
        }
    }
}