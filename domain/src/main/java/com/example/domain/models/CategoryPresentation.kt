package com.example.domain.models

data class CategoryPresentation(
    val id: Long,
    val name: String,
    val slug: String,
    var select: Boolean,
)
