package com.example.posters.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.domain.models.PosterDetailPresentation
import com.example.domain.models.ScreenState
import com.example.initi_test_project.ui.screens.base.ScreenError
import com.example.initi_test_project.ui.screens.base.ScreenLoading

@Composable
fun DetailPoster(
    navController: NavHostController,
    posterId: Long,
) {
    val viewModel: DetailPosterViewModel = hiltViewModel()
    viewModel.getPoster(posterId)

    val poster by viewModel.poster.collectAsState()

    when(poster.screenState){
        ScreenState.DEFAULT->{}
        ScreenState.LOADING->{
            ScreenLoading()
        }
        ScreenState.ERROR->{
            ScreenError(poster.errorText.toString())
        }
        ScreenState.RESULT->{DetailPosterContent(poster =poster.data!!, toBack = {navController.popBackStack()})}
    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DetailPosterContent(
    poster: PosterDetailPresentation,
    toBack: ()-> Unit) {
    val scrollState = rememberScrollState()
    Scaffold() {paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(horizontal = 16.dp).verticalScroll(scrollState)) {
            IconButton(onClick = { toBack() }) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = " back")
            }
                GlideImage(model = poster.place.images!!,contentDescription = null)
            Spacer(modifier = Modifier.height(16.dp))
            Text(poster.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text(poster.description)
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                poster.startData?.let{date->
                    Text("c ${date} ")
                }
                poster.endData?.let{date->
                    Text("до ${date}")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text("Адрес:")
            Spacer(modifier = Modifier.height(16.dp))
            Text(poster.place.address.toString())
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                buildAnnotatedString {
                    withLink(
                        LinkAnnotation.Url(
                            poster.place.siteUrl.toString(),
                            TextLinkStyles(style = SpanStyle(color = Color.Blue))
                        )
                    ) {
                        append("Сайт")
                    }
                }

            )


        }
    }
}