package com.example.domain.models

data class PosterPresentation(
    val id: Long,
    val location: Location,
    val categories: List<String>,
)
