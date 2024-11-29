package com.example.data.models

data class Event(
    val categories: List<String>,
    val dates: List<Date>,
    val description: String,
    val id: Long,
    val images: List<Image>,
    val place: PlaceId?,
    val slug: String,
    val title: String,
    val coords: Coords?,
)
