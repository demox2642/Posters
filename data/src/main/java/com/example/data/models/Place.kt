package com.example.data.models

import com.google.gson.annotations.SerializedName

data class Place(
    val address: String,
    @SerializedName("body_text")
    val bodyText: String,
    val categories: List<String>,
    val coords: Coords,
    val description: String,
    val id: Long,
    val images: List<Image>,
    val location: String,
    val phone: String,
    @SerializedName("short_title")
    val shortTitle: String,
    @SerializedName("site_url")
    val siteUrl: String,
    val slug: String,
    val subway: String,
    val tags: List<String>,
    val timetable: String,
    val title: String,
)
