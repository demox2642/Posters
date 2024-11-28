package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.database.LocalDatabaseKudaGo
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.Location
import com.example.domain.models.PosterDetailPresentation
import com.example.domain.models.PosterPresentation
import com.example.domain.repository.LocalRepository
import javax.inject.Inject

class LocalRepositoryImpl
    @Inject
    constructor(
        private val database: LocalDatabaseKudaGo,
    ) : LocalRepository {
        override suspend fun getPosterList(
            location: Location?,
            categories: String?,
            radius: Long?,
            error: (String) -> Unit,
        ): PagingData<PosterPresentation> {
            TODO("Not yet implemented")
        }

        override suspend fun getPosterDetail(id: Long): PosterDetailPresentation = database.posterDao().getPoster(id)

        override suspend fun getCategoryList(): List<CategoryPresentation> {
            TODO("Not yet implemented")
        }
    }
