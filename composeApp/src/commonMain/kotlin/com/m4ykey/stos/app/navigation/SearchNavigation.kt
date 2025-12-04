package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.search.presentation.SearchListScreen
import com.m4ykey.stos.search.presentation.SearchScreen

fun NavGraphBuilder.searchNavigation(navHostController: NavHostController) {
    composable(route = Screen.Search.route) {
        SearchScreen(
            onBack = { navHostController.navigateUp() },
            onSearchScreen = { inTitle, tag ->
                navHostController.navigate(Screen.SearchList.routeWithArgs(inTitle, tag)) {
                    launchSingleTop = true
                }
            }
        )
    }

    composable(
        route = Screen.SearchList.route,
        arguments = Screen.SearchList.arguments
    ) { navBackStackEntry ->
        val tag = navBackStackEntry.savedStateHandle.get<String>(Screen.SearchList.ARG_SEARCH_TAG) ?: return@composable
        val inTitle = navBackStackEntry.savedStateHandle.get<String>(Screen.SearchList.ARG_SEARCH_IN_TITLE) ?: return@composable

        SearchListScreen(
            onBack = { navHostController.navigateUp() },
            tag = tag,
            inTitle = inTitle,
            onQuestionClick = { questionId ->
                navHostController.navigate(Screen.QuestionDetail.routeWithArgs(questionId)) {
                    launchSingleTop = true
                }
            }
        )
    }
}