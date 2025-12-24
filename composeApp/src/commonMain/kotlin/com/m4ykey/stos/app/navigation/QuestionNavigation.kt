package com.m4ykey.stos.app.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.m4ykey.stos.question.presentation.comment.QuestionCommentListScreen
import com.m4ykey.stos.question.presentation.detail.QuestionDetailScreen
import com.m4ykey.stos.question.presentation.list.QuestionListScreen
import com.m4ykey.stos.question.presentation.related.QuestionRelatedScreen
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
            },
            onUserClick = { id ->
                navHostController.navigate(Screen.UserScreen.routeWithArgs(id)) {
                    launchSingleTop = true
                }
            }
        )
    }
    composable(
        route = Screen.QuestionDetail.route,
        arguments = Screen.QuestionDetail.arguments
    ) { backStack ->
        val id = backStack.savedStateHandle.get<Int>(Screen.QuestionDetail.ARG_QUESTION_ID) ?: return@composable
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
                navHostController.navigate(Screen.QuestionRelated.routeWithArgs(id))
            },
            onUserClick = { id ->
                navHostController.navigate(Screen.UserScreen.routeWithArgs(id))
            }
        )
    }
    composable(
        route = Screen.QuestionTag.route,
        arguments = Screen.QuestionTag.arguments
    ) { backStack ->
        val tag = backStack.savedStateHandle.get<String>(Screen.QuestionTag.ARG_QUESTION_TAG) ?: return@composable
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
    ) { backStack ->
        val id = backStack.savedStateHandle.get<Int>(Screen.QuestionComment.ARG_QUESTION_ID) ?: return@composable
        QuestionCommentListScreen(
            id = id,
            onBack = { navHostController.popBackStack() },
            onUserClick = { id ->
                navHostController.navigate(Screen.UserScreen.routeWithArgs(id)) {
                    launchSingleTop = true
                }
            }
        )
    }

    composable(
        route = Screen.QuestionRelated.route,
        arguments = Screen.QuestionRelated.arguments
    ) { backStack ->
        val id = backStack.savedStateHandle.get<Int>(Screen.QuestionRelated.ARG_QUESTION_ID) ?: return@composable
        QuestionRelatedScreen(
            onBack = { navHostController.navigateUp() },
            id = id,
            onQuestionClick = { id ->
                navHostController.navigate(Screen.QuestionDetail.routeWithArgs(id)) {
                    launchSingleTop = true
                }
            }
        )
    }
}