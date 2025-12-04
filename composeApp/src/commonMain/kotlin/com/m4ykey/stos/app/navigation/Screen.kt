package com.m4ykey.stos.app.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen(val route : String) {

    @Serializable
    object QuestionHome : Screen(route = "question_home")

    @Serializable
    object Search : Screen(route = "search")

    @Serializable
    data class QuestionDetail(val id : Int) : Screen("$routeBase/$id") {
        companion object {
            const val routeBase = "question_detail_screen"
            const val ARG_QUESTION_ID = "id"

            fun routeWithArgs(id : Int) : String = "$routeBase/$id"

            val arguments = listOf(
                navArgument(ARG_QUESTION_ID) { type = NavType.IntType }
            )

            val route = "${routeBase}/{${ARG_QUESTION_ID}}"
        }
    }

    @Serializable
    data class QuestionTag(val tag : String) : Screen("$routeBase/$tag") {
        companion object {
            const val routeBase = "question_tag_screen"
            const val ARG_QUESTION_TAG = "tag"

            fun routeWithArgs(tag : String) : String = "$routeBase/$tag"

            val arguments = listOf(
                navArgument(ARG_QUESTION_TAG) { type = NavType.StringType }
            )

            val route = "${routeBase}/{${ARG_QUESTION_TAG}}"
        }
    }

    @Serializable
    data class QuestionComment(val id : Int) : Screen("$routeBase/$id") {
        companion object {
            const val routeBase = "question_comment_screen"
            const val ARG_QUESTION_ID = "id"

            fun routeWithArgs(id : Int) : String = "$routeBase/$id"

            val arguments = listOf(
                navArgument(ARG_QUESTION_ID) { type = NavType.IntType }
            )

            val route = "${routeBase}/{${ARG_QUESTION_ID}}"
        }
    }

    @Serializable
    data class SearchList(val inTitle : String, val tagged : String) : Screen("$routeBase/$inTitle/$tagged") {
        companion object {
            const val routeBase = "search_list_screen"
            const val ARG_SEARCH_TAG = "tagged"
            const val ARG_SEARCH_IN_TITLE = "intitle"

            fun routeWithArgs(inTitle: String, tag: String) : String = "$routeBase/$inTitle/$tag"

            val arguments = listOf(
                navArgument(ARG_SEARCH_IN_TITLE) { type = NavType.StringType },
                navArgument(ARG_SEARCH_TAG) { type = NavType.StringType }
            )

            val route = "${routeBase}/{${ARG_SEARCH_IN_TITLE}}/{${ARG_SEARCH_TAG}}"
        }
    }
}