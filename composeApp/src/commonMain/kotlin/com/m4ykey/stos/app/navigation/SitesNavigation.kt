package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.sites.presentation.SitesScreen

fun NavGraphBuilder.sitesNavigation(navHostController: NavHostController) {
    composable(Screen.SitesScreen.route) {
        SitesScreen(
            onBack = {
                navHostController.navigateUp()
            }
        )
    }
}