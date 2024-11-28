package com.example.data.models

import com.google.gson.annotations.SerializedName

data class ServerResponse(
    val next: String?,
    val previous: String?,
    val results: List<Event>?,
    @SerializedName("detail")
    val error: String?,
)
