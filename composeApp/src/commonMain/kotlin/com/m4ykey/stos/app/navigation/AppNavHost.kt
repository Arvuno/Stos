package com.m4ykey.stos.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavHost(
    modifier : Modifier = Modifier
) {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController,
        startDestination = Screen.QuestionHome.route,
        modifier = modifier
    ) {
        questionNavigation(navHostController)
        searchNavigation(navHostController)
        userNavigation(navHostController)
        sitesNavigation(navHostController)
    }
}