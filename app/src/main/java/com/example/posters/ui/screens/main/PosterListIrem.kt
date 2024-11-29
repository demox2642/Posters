package com.example.posters.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.domain.models.PosterPresentation
import com.example.posters.ui.base.toPresentFormat

@Composable
fun PosterListItem(poster: PosterPresentation,toDetail: (Long)->Unit){
    Card(
        modifier = Modifier.padding(vertical = 4.dp),
        onClick = {toDetail(poster.id)} ) {
        Row (modifier = Modifier.fillMaxWidth()){
            Column(modifier = Modifier.padding( 16.dp).fillMaxWidth(0.7F)) {
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(poster.title, style = MaterialTheme.typography.titleMedium)
                }
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text(poster.description,
                        style = MaterialTheme.typography.bodyLarge,
                        maxLines = 3
                    )
                }
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    poster.startData?.let{date->
                        Text("c ${date.toPresentFormat()} ")
                    }
                    poster.endData?.let{date->
                        Text("до ${date.toPresentFormat()}")
                    }
                }
            }
            Column(modifier = Modifier.padding( 16.dp).fillMaxWidth(0.7F)) {
                Text("${poster.location}")
            }
        }

    }
}