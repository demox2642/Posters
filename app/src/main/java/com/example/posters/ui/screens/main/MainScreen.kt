package com.example.posters.ui.screens.main

import android.util.Log
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.PosterPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.initi_test_project.ui.screens.base.ScreenError
import com.example.initi_test_project.ui.screens.base.ScreenLoading
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback

private var locationCallback: LocationCallback? = null
 private var fusedLocationClient: FusedLocationProviderClient? = null

@Composable
fun MainScreen(navController: NavHostController) {
    val viewModel: MainScreenViewModel = hiltViewModel()

    viewModel.getCategoryList()
    viewModel.getPosterList()

    val categoryList by viewModel.categoryList.collectAsState()
    val posterList by viewModel.posterList.collectAsState()

    when(categoryList.screenState){
        ScreenState.RESULT->{
            MainScreenContent(
                data= categoryList.data,
                posterList=posterList,
                changeItemState = viewModel::changeCategoryItemState
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
    posterList: PresentationModel<PagingData<PosterPresentation>>,
    changeItemState:(Long)->Unit
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

        }
    }
}

@Composable
fun FilterBar(data: List<CategoryPresentation>?,
              changeItemState:(Long)->Unit) {
    Log.e("Data","$data")
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(30.dp))
        Text("SelectFilters:", style = MaterialTheme.typography.titleMedium)
        data?.let {
            LazyRow {
                items(data.filter { it.select }){
                    ExtendedFloatingActionButton(
                        modifier = Modifier.padding(4.dp),
                        onClick = { changeItemState(it.id) },
                        icon = { Icon(Icons.Filled.Close, "close") },
                        text = { Text(text = it.name) },
                        contentColor= Color.Green
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Filters:", style = MaterialTheme.typography.titleMedium)
            LazyRow  {
                items(data.filter { it.select.not() }){
                    ExtendedFloatingActionButton(
                        modifier = Modifier.padding(4.dp),
                        onClick = { changeItemState(it.id) },
                        icon = {},
                        text = { Text(text = it.name) },
                    )
                }
            }
        }

    }


}