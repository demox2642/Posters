package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repository.InternetRepository
import com.example.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPosterListUseCase(
    private val localRepository: LocalRepository,
    private val internetRepository: InternetRepository,
) {
    suspend fun getPosterList(
        location: Location?,
        categories: String?,
        radius: Long?,
        internetIsConnect: Boolean,
    ): Flow<PresentationModel<PagingData<PosterPresentation>>> =
        flow {
            var response = PresentationModel<PagingData<PosterPresentation>>(screenState = ScreenState.LOADING)

            if (internetIsConnect)
                {
                    val internetResponse =
                        internetRepository.getPosterList(
                            location = location,
                            categories = categories,
                            radius = radius,
                        ) {
                            response =
                                PresentationModel<PagingData<PosterPresentation>>(
                                    screenState = ScreenState.RESULT,
                                    errorText = it,
                                )
                        }
                    internetResponse.collect{
                        response =
                            PresentationModel<PagingData<PosterPresentation>>(
                                screenState = ScreenState.RESULT,
                                data = it,
                            )
                    }

                } else {
                val localResponse =
                    localRepository.getPosterList(
                        location = location,
                        categories = categories,
                        radius = radius,
                    ) {
                        response =
                            PresentationModel<PagingData<PosterPresentation>>(
                                screenState = ScreenState.RESULT,
                                errorText = it,
                            )
                    }

                response =
                    PresentationModel<PagingData<PosterPresentation>>(
                        screenState = ScreenState.RESULT,
                        data = localResponse,
                    )
            }

            emit(response)
        }.flowOn(Dispatchers.IO)
}
