package com.example.domain.usecase

import com.example.domain.models.CategoryPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GetCategoryListUseCase(
    private val localRepository: LocalRepository,
) {
    suspend fun getCategoryList(): Flow<PresentationModel<List<CategoryPresentation>>> =
        flow {
            var response = PresentationModel<List<CategoryPresentation>>(screenState = ScreenState.LOADING)
            response =
                if (localRepository.getCategoryList().isEmpty()) {
                    PresentationModel<List<CategoryPresentation>>(
                        screenState = ScreenState.RESULT,
                        errorText = "Список категорий недоступен. Проверьте соединение с интернетом",
                    )
                } else {
                    PresentationModel<List<CategoryPresentation>>(
                        screenState = ScreenState.RESULT,
                        data = localRepository.getCategoryList(),
                    )
                }
            emit(response)
        }.flowOn(Dispatchers.IO)
}
