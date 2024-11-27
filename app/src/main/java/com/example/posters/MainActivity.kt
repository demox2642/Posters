package com.example.posters

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.posters.ui.screens.HomeScreens
import com.example.posters.ui.screens.homeScreens
import com.example.posters.ui.theme.PostersTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            PostersTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = HomeScreens.HomeMainScreen.route,
                ) {
                    homeScreens(navController)
                }
            }
        }
    }
}
