package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.Location
import com.example.domain.models.PosterDetailPresentation
import com.example.domain.models.PosterPresentation

interface LocalRepository {
    suspend fun getPosterList(
        location: Location?,
        categories: String?,
        radius: Long?,
        error: (String) -> Unit,
    ): PagingData<PosterPresentation>

    suspend fun getPosterDetail(id: Long): PosterDetailPresentation?

    suspend fun getCategoryList(): List<CategoryPresentation>
}
