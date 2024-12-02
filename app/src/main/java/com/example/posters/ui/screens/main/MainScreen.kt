@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.posters.ui.screens.main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Looper
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.LoadState.Error
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
import com.example.domain.models.ScreenState
import com.example.initi_test_project.ui.screens.base.ScreenError
import com.example.initi_test_project.ui.screens.base.ScreenLoading
import com.example.posters.R
import com.example.posters.ui.base.DialogYesNo
import com.example.posters.ui.screens.HomeScreens
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority


private var locationCallback: LocationCallback? = null
 private var fusedLocationClient: FusedLocationProviderClient? = null

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainScreenViewModel = hiltViewModel()

    viewModel.getCategoryList()

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    val categoryList by viewModel.categoryList.collectAsState()
    val posterList = viewModel.posterList.collectAsLazyPagingItems()
    val openDialog = remember { mutableStateOf(locationPermissionsState.allPermissionsGranted.not()) }
    val dialogState by viewModel.dialogState.collectAsState()
    val location by viewModel.location.collectAsState()
    if (dialogState) {

        MultiplePermissions(
            dialogState = openDialog.value,
            changeDialogState = {
                if (it.not()){
                    viewModel.changeDialogState()
                }
                openDialog.value = it },
            per = {
                if (it){
                    startLocationUpdates()
                }
            })
    }

    val context = LocalContext.current
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (lo in p0.locations) {
                viewModel.saveLocation(Location(lat = lo.latitude, lon =  lo.longitude))
            }
        }
    }

    when(categoryList.screenState){
        ScreenState.RESULT->{

            MainScreenContent(
                data= categoryList.data,
                changeItemState = viewModel::changeCategoryItemState,
                posterList = posterList,
                location = location,
                toDetail = {
                    navController.navigate(HomeScreens.DetailPoster.route + "/$it")
                }
            )
        }
        ScreenState.DEFAULT->{}
        ScreenState.LOADING->{
            ScreenLoading()
        }
        ScreenState.ERROR->{
            ScreenError(categoryList.errorText.toString())
        }
    }
}

@Composable
fun MainScreenContent(
    data: List<CategoryPresentation>?,
    changeItemState: (Long) -> Unit,
    posterList: LazyPagingItems<PosterPresentation>,
    location: Location?,
    toDetail: (Long) -> Unit,
) {

    Scaffold(   modifier = Modifier.fillMaxSize(),
        topBar = {
            FilterBar(
                data = data,
                changeItemState = changeItemState
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 16.dp)) {
            when(posterList.loadState.refresh){
                is LoadState.Loading -> {
                    ScreenLoading()
                }
                is LoadState.Error -> {
                   ScreenError((posterList.loadState.refresh as Error).error.message.toString())
                }
                else->{

                    LazyColumn {
                       items(posterList.itemCount){index->
                           posterList[index]?.let{poster->
                               PosterListItem(poster, toDetail = toDetail, location)
                           }

                       }
                    }
                }
            }

        }
    }
}

@Composable
fun FilterBar(data: List<CategoryPresentation>?,
              changeItemState:(Long)->Unit) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("SelectFilters:", style = MaterialTheme.typography.titleMedium)
        data?.let {
            LazyRow {
                items(data.filter { it.select }){
                    ExtendedFloatingActionButton(

                        modifier = Modifier.padding(4.dp).height(40.dp),
                        onClick = { changeItemState(it.id) },
                        icon = { Icon(Icons.Filled.Close, "close") },
                        text = { Text(text = it.name) },
                        contentColor= MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Filters:", style = MaterialTheme.typography.titleMedium)
            LazyRow  {
                items(data.filter { it.select.not() }){
                    ExtendedFloatingActionButton(
                        modifier = Modifier.padding(4.dp).height(40.dp),
                        onClick = { changeItemState(it.id) },
                        icon = {},
                        text = { Text(text = it.name) },
                    )
                }
            }
        }

    }

}

@ExperimentalPermissionsApi
@Composable
fun MultiplePermissions(
    dialogState:Boolean,
    changeDialogState:(Boolean)->Unit,
    per: (Boolean) -> Unit
) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    val context = LocalContext.current

    PermissionsRequired(
        multiplePermissionsState = locationPermissionsState,
        permissionsNotGrantedContent = {changeDialogState(true)},
        permissionsNotAvailableContent = {changeDialogState(true)},

    ) {
        per(locationPermissionsState.permissions.none {
            it.hasPermission.not()
        })
    }
    if (dialogState) {
        DialogYesNo(
            title = stringResource(R.string.permission_error_title),
            text = stringResource(R.string.permission_error_text),
            no = { changeDialogState(false)  },
            yes = {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", context.packageName, null)
                )
                context.startActivity(intent)
            },
            )
    }
}

@SuppressLint("MissingPermission")
private fun startLocationUpdates() {
    locationCallback?.let {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 300000)
            .build()

        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper(),
        )
    }
}
