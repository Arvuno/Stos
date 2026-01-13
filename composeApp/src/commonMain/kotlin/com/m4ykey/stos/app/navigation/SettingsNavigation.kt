package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.settings.presentation.LibraryScreen
import com.m4ykey.stos.settings.presentation.SettingsScreen

fun NavGraphBuilder.settingsNavigation(navHostController: NavHostController) {
    composable(Screen.SettingsScreen.route) {
        SettingsScreen(
            onBack = {
                navHostController.navigateUp()
            },
            onLibrariesClick = {
                navHostController.navigate(Screen.LibraryScreen.route)
            }
        )
    }

    composable(Screen.LibraryScreen.route) {
        LibraryScreen(
            onBack = { navHostController.navigateUp() }
        )
    }
}