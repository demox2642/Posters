package com.example.posters.ui.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold {
        Text(text = "MainScreen")
    }
}
