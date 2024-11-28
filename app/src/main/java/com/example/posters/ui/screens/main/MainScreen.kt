package com.example.posters.ui.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

// private var locationCallback: LocationCallback? = null
// private var fusedLocationClient: FusedLocationProviderClient? = null

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainScreenViewModel = hiltViewModel()

    viewModel.getCategoryList()
    viewModel.getPosterList()

    val categoryList by viewModel.categoryList.collectAsState()
    val posterList by viewModel.posterList.collectAsState()

    Scaffold {
        Text(text = "MainScreen")
    }
}
