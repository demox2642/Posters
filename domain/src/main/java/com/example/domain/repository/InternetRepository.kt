package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
import kotlinx.coroutines.flow.Flow

interface InternetRepository {
    suspend fun getPosterList(
        location: Location?,
        categories: String?,
        radius: Long?,
        error: (String) -> Unit,
    ): Flow<PagingData<PosterPresentation>>
}
