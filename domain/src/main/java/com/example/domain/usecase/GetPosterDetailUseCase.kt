package com.example.domain.usecase

import com.example.domain.models.PosterDetailPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetPosterDetailUseCase(
    private val localRepository: LocalRepository,
) {
    suspend fun getPosterDetail(id: Long): Flow<PresentationModel<PosterDetailPresentation>> =
        flow {
            var response = PresentationModel<PosterDetailPresentation>(screenState = ScreenState.LOADING)
            response =
                if (localRepository.getPosterDetail(id) == null)
                    {
                        PresentationModel<PosterDetailPresentation>(
                            screenState = ScreenState.RESULT,
                            errorText = "Событие не найдено. Проверьте соединение с интернетом",
                        )
                    } else {
                    PresentationModel<PosterDetailPresentation>(
                        screenState = ScreenState.RESULT,
                        data = localRepository.getPosterDetail(id),
                    )
                }
            emit(response)
        }.flowOn(Dispatchers.IO)
}
