package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.database.LocalDatabaseKudaGo
import com.example.data.models.toPlacePresentation
import com.example.data.models.toPosterPresentation
import com.example.domain.models.CategoryPresentation
import com.example.domain.models.Location
import com.example.domain.models.PlacePresentation
import com.example.domain.models.PosterDetailPresentation
import com.example.domain.models.PosterPresentation
import com.example.domain.repository.LocalRepository
import kotlinx.coroutines.delay
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

        override suspend fun getPosterDetail(id: Long): PosterDetailPresentation {
            val posterDB =database.posterDao().getPoster(id)
            val placeDB = database.placeDao().getPlace(posterDB.placeId)

            return PosterDetailPresentation(
                 id=posterDB.id,
             place = placeDB.toPlacePresentation(),
             startData = posterDB.startData,
             endData = posterDB.endData,
             title = posterDB.title,
             description = posterDB.description,
            )
        }

        override suspend fun getCategoryList(): List<CategoryPresentation> {
           return  database.categoryDao().getCategoryList().map {
               CategoryPresentation(id = it.id, name = it.name, slug = it.slug, select = it.select)
           }
        }
    }
