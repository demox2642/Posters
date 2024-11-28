package com.example.domain.models

import java.util.Date

data class PosterPresentation(
    val id: Long,
    val location: Location,
    val categories: List<String>,
    val startData: Date?,
    val endData: Date?,
    val title: String,
    val description: String,
)
