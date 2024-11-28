package com.example.posters.di

import com.example.domain.repository.InternetRepository
import com.example.domain.repository.LocalRepository
import com.example.domain.usecase.GetCategoryListUseCase
import com.example.domain.usecase.GetPosterDetailUseCase
import com.example.domain.usecase.GetPosterListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun providesGetPosterDetailUseCase(localRepository: LocalRepository): GetPosterDetailUseCase = GetPosterDetailUseCase(localRepository)

    @Provides
    fun providesGetPosterListUseCase(
        localRepository: LocalRepository,
        internetRepository: InternetRepository,
    ): GetPosterListUseCase = GetPosterListUseCase(localRepository, internetRepository)

    @Provides
    fun providesGetCategoryListUseCase(localRepository: LocalRepository): GetCategoryListUseCase = GetCategoryListUseCase(localRepository)
}
