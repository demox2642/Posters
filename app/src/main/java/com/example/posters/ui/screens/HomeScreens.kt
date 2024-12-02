package com.example.posters.ui.screens

import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.posters.ui.screens.detail.DetailPoster
import com.example.posters.ui.screens.main.MainScreen

sealed class HomeScreens(
    val route: String,
) {
    object HomeMainScreen : HomeScreens("home_main_screen")

    object DetailPoster : HomeScreens("detail_poster")
}

fun NavGraphBuilder.homeScreens(navController: NavHostController) {
    composable(HomeScreens.HomeMainScreen.route) { MainScreen(navController) }
    composable(
        HomeScreens.DetailPoster.route.plus("/{poster_id}"),
        arguments = listOf(
            navArgument("poster_id") {
                type = NavType.LongType
            },
        ),
    ) { backStackEntry ->
        val arguments = remember(backStackEntry) {
            (backStackEntry.arguments?.getLong("poster_id"))
        }
        DetailPoster(navController, arguments!!)
    }
}
