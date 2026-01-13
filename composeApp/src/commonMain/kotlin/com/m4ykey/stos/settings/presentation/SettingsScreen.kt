@file:OptIn(ExperimentalMaterial3Api::class)

package com.m4ykey.stos.settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.m4ykey.core.views.ActionIconButton
import com.m4ykey.core.views.AppScaffold
import com.m4ykey.stos.core.network.openBrowser
import kmp_stos.composeapp.generated.resources.Res
import kmp_stos.composeapp.generated.resources.api_provided
import kmp_stos.composeapp.generated.resources.arrow_left
import kmp_stos.composeapp.generated.resources.back
import kmp_stos.composeapp.generated.resources.github
import kmp_stos.composeapp.generated.resources.library
import kmp_stos.composeapp.generated.resources.open_source_libraries
import kmp_stos.composeapp.generated.resources.power
import kmp_stos.composeapp.generated.resources.powered_by
import kmp_stos.composeapp.generated.resources.settings
import kmp_stos.composeapp.generated.resources.source_code
import kmp_stos.composeapp.generated.resources.used_libraries
import kmp_stos.composeapp.generated.resources.visit_project_on_github
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsScreen(
    onBack : () -> Unit,
    onLibrariesClick : () -> Unit
) {
    val state = rememberLazyListState()

    AppScaffold(
        title = Res.string.settings,
        content = { padding ->
            LazyColumn(
                state = state,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                item {
                    SettingsItem(
                        title = Res.string.source_code,
                        message = Res.string.visit_project_on_github,
                        onClick = { openBrowser("https://github.com/m4ykey/Stos") },
                        icon = Res.drawable.github
                    )
                }
                item {
                    SettingsItem(
                        title = Res.string.powered_by,
                        message = Res.string.api_provided,
                        onClick = { openBrowser("https://api.stackexchange.com/docs") },
                        icon = Res.drawable.power
                    )
                }
                item {
                    SettingsItem(
                        title = Res.string.used_libraries,
                        message = Res.string.open_source_libraries,
                        onClick = { onLibrariesClick() },
                        icon = Res.drawable.library
                    )
                }
            }
        },
        navigation = {
            ActionIconButton(
                text = Res.string.back,
                icon = Res.drawable.arrow_left,
                onClick = onBack
            )
        }
    )
}

@Composable
fun SettingsItem(
    modifier : Modifier = Modifier,
    title : StringResource,
    message : StringResource,
    onClick : () -> Unit,
    icon : DrawableResource
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.padding(end = 12.dp)
        )
        Column(
            modifier = modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = stringResource(title),
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = stringResource(message),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }
}