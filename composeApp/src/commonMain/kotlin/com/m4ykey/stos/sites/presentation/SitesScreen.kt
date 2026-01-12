@file:OptIn(ExperimentalMaterial3Api::class)

package com.m4ykey.stos.sites.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.paging.compose.collectAsLazyPagingItems
import com.m4ykey.core.views.ActionIconButton
import com.m4ykey.core.views.AppScaffold
import com.m4ykey.core.views.BasePagingList
import com.m4ykey.stos.sites.presentation.components.SitesItem
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SitesScreen(
    modifier : Modifier = Modifier,
    onBack : () -> Unit,
    viewModel: SitesViewModel = koinViewModel()
) {
    val items = viewModel.sites.collectAsLazyPagingItems()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val listState = rememberLazyListState()

    AppScaffold(
        navigation = {
            ActionIconButton(
                onClick = onBack,
                text = Res.string.back,
                icon = Res.drawable.arrow_left
            )
        },
        content = { padding ->
            BasePagingList(
                modifier = modifier.padding(padding),
                itemContent = { site ->
                    SitesItem(item = site)
                },
                items = items,
                listState = listState,
                itemKey = { it.siteUrl }
            )
        },
        scrollBehavior = scrollBehavior,
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    )
}