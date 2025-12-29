package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.user.presentation.UserScreen

fun NavGraphBuilder.userNavigation(navHostController: NavHostController) {
    composable(
        arguments = Screen.UserScreen.arguments,
        route = Screen.UserScreen.route
    ) { backStack ->
        val id = backStack.savedStateHandle.get<Int>(Screen.UserScreen.ARG_USER_ID) ?: return@composable
        UserScreen(
            id = id,
            onBack = {
                navHostController.navigateUp()
            }
        )
    }
}