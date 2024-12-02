package com.example.posters.ui.screens.main

import android.location.Location
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.domain.models.PosterPresentation
import com.example.posters.ui.base.toPresentFormat
import kotlin.math.roundToInt

@Composable
fun PosterListItem(poster: PosterPresentation, toDetail: (Long) -> Unit, location: com.example.domain.models.Location?){
    Card(
        modifier = Modifier.padding(vertical = 4.dp),
        onClick = { toDetail(poster.id)} ) {
        Row (modifier = Modifier.fillMaxWidth()){
            Column(modifier = Modifier.padding( 16.dp).fillMaxWidth(0.7F)) {
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(poster.title, style = MaterialTheme.typography.titleMedium)
                }
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(poster.description,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 3,
                                overflow = TextOverflow.Ellipsis,
                    )
                }
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    poster.startData?.toPresentFormat()?.let{date->
                        Text("c ${date} ")
                    }
                    poster.endData?.toPresentFormat()?.let{date->
                        Text("до ${date}")
                    }
                }
            }
            Column(modifier = Modifier.padding( 16.dp).fillMaxWidth(0.7F)) {

                if (location != null){
                    val locationPoster = Location("Poster")
                    val locationUser = Location("User")
                    poster.location?.let {
                        if (it.lat != null && it.lon != null){
                            locationPoster.latitude = it.lat!!
                            locationPoster.longitude = it.lon!!
                        }

                    }
                    locationUser.longitude = location.lon!!
                    locationUser.latitude = location.lat!!

                    Text("${locationPoster.distanceTo(locationUser).roundToInt()}m")
                }else{
                    Text("Не удалось определить")
                }

            }
        }

    }
}