package com.example.domain.models

data class PlacePresentation(
    val address: String,
    val bodyText: String,
    val categories: List<String>,
    val description: String,
    val id: Int,
    val images: List<String>,
    val city: String,
    val phone: String,
    val shortTitle: String,
    val siteUrl: String,
    val timetable: String,
    val title: String,
    val location: Location,
)
