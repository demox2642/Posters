package com.example.posters.ui.screens.main

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback

private var locationCallback: LocationCallback? = null
private var fusedLocationClient: FusedLocationProviderClient? = null

@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold {
        Text(text = "MainScreen")
    }
}
