package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.database.LocalDatabaseKudaGo
import com.example.data.database.models.CategoriesDB
import com.example.data.repository.pagingdata.PosterPagingData
import com.example.data.service.KudaGoService
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
import com.example.domain.repository.InternetRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InternetRepositoryImpl
    @Inject
    constructor(
        private val service: KudaGoService,
        private val database: LocalDatabaseKudaGo,
    ) : InternetRepository {
        override suspend fun getPosterList(
            location: Location?,
            categories: String?,
            radius: Long?,
            error: (String) -> Unit,
        ): Flow<PagingData<PosterPresentation>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 5,
                        enablePlaceholders = false,
                        initialLoadSize = 1,
                    ),
                pagingSourceFactory = {
                    PosterPagingData(
                        service = service,
                        databaseKudaGo = database,
                        location = location,
                        categories = categories,
                        radius = radius,
                        error = error,
                    )
                },
            ).flow

    override suspend fun getCategoryes(): List<CategoryPresentation> {
        val response = service.getCategoryList()
        database.categoryDao().addAllCategorie(response.map { CategoriesDB(
            id = it.id,
            name = it.name,
            slug = it.slug,
        ) })

        return response.map {
            CategoryPresentation(
                id = it.id,
                name = it.name,
                slug = it.slug,
                select = false
            )
        }
    }
}
