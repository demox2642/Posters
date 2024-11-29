package com.example.domain.usecase

import com.example.domain.models.CategoryPresentation
import com.example.domain.models.PresentationModel
import com.example.domain.models.ScreenState
import com.example.domain.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ChangeFilterStateUseCase(
    private val localRepository: LocalRepository
) {
    suspend fun updateCategoryList(categoryId: Long): Flow<PresentationModel<List<CategoryPresentation>>> =
        flow {
            var response =
                PresentationModel<List<CategoryPresentation>>(screenState = ScreenState.LOADING)
            response = PresentationModel<List<CategoryPresentation>>(
                screenState = ScreenState.RESULT,
                data = localRepository.updateCategoryList(categoryId)
            )
            emit(response)
        }.flowOn(Dispatchers.IO)
}