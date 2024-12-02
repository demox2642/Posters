package com.example.domain.usecase

import androidx.paging.PagingData
import com.example.domain.models.Location
import com.example.domain.models.PosterPresentation
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
        error: (String) -> Unit,
    ): Flow<PagingData<PosterPresentation>> {

     return   if (internetIsConnect){
                        internetRepository.getPosterList(
                            location = location,
                            categories = categories,
                            radius = radius,
                            error = error
                        )
        }else{
                  flow {
                      var result = PagingData.empty<PosterPresentation>()
                      result = localRepository.getPosterList(
                          location = location,
                          categories = categories,
                          radius = radius,
                          error = error
                      )
                      emit(result)
                  } .flowOn(Dispatchers.IO)

        }

    }

}
