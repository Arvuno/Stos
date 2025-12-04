package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.question.presentation.comment.QuestionCommentListScreen
import com.m4ykey.stos.question.presentation.detail.QuestionDetailScreen
import com.m4ykey.stos.question.presentation.list.QuestionListScreen
import com.m4ykey.stos.question.presentation.tag.QuestionTagScreen

fun NavGraphBuilder.questionNavigation(navHostController: NavHostController) {
    composable(Screen.QuestionHome.route) {
        QuestionListScreen(
            onQuestionClick = { id ->
                navHostController.navigate(Screen.QuestionDetail.routeWithArgs(id)) {
                    launchSingleTop = true
                }
            },
            onSearch = {
                navHostController.navigate(Screen.Search.route) {
                    launchSingleTop = true
                }
            }
        )
    }
    composable(
        route = Screen.QuestionDetail.route,
        arguments = Screen.QuestionDetail.arguments
    ) { navBackStackEntry ->
        val id = navBackStackEntry.savedStateHandle.get<Int>(Screen.QuestionDetail.ARG_QUESTION_ID) ?: return@composable
        QuestionDetailScreen(
            onBack = { navHostController.popBackStack() },
            id = id,
            onTagClick = { tag ->
                navHostController.navigate(Screen.QuestionTag.routeWithArgs(tag))
            },
            onCommentClick = { id ->
                navHostController.navigate(Screen.QuestionComment.routeWithArgs(id))
            },
            onRelatedClick = { id ->

            }
        )
    }
    composable(
        route = Screen.QuestionTag.route,
        arguments = Screen.QuestionTag.arguments
    ) { navBackStackEntry ->
        val tag = navBackStackEntry.savedStateHandle.get<String>(Screen.QuestionTag.ARG_QUESTION_TAG) ?: return@composable
        QuestionTagScreen(
            tag = tag,
            onBack = { navHostController.popBackStack() },
            onQuestionClick = { id ->
                navHostController.navigate(Screen.QuestionDetail.routeWithArgs(id)) {
                    launchSingleTop = true
                }
            }
        )
    }
    composable(
        route = Screen.QuestionComment.route,
        arguments = Screen.QuestionComment.arguments
    ) { navBackStackEntry ->
        val id = navBackStackEntry.savedStateHandle.get<Int>(Screen.QuestionComment.ARG_QUESTION_ID) ?: return@composable
        QuestionCommentListScreen(
            id = id,
            onBack = { navHostController.popBackStack() }
        )
    }
}