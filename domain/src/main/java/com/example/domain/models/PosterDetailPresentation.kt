package com.example.domain.models

data class PosterDetailPresentation(
    val id: Long,
    val place: PlacePresentation,
    val startData: Long?,
    val endData: Long?,
    val title: String,
    val description: String,
)
