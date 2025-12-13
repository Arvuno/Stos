package com.m4ykey.stos.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost() {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = Screen.QuestionHome.route
    ) {
        questionNavigation(navHostController)
        searchNavigation(navHostController)
        userNavigation(navHostController)
    }
}