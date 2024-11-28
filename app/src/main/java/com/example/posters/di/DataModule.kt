package com.example.posters.di

import com.example.data.database.LocalDatabaseKudaGo
import com.example.data.repository.InternetRepositoryImpl
import com.example.data.repository.LocalRepositoryImpl
import com.example.data.service.KudaGoService
import com.example.domain.repository.InternetRepository
import com.example.domain.repository.LocalRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    fun provideUrl(): String = " https://kudago.com/public-api/v1.4/"

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        baseUrl: String,
        client: OkHttpClient,
    ): Retrofit.Builder {
        val gson =
            GsonBuilder()
                .setLenient()
                .create()

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideSearchApi(retrofit: Retrofit.Builder): KudaGoService =
        retrofit
            .build()
            .create(KudaGoService::class.java)

    @Singleton
    @Provides
    fun provideLocalRepository(database: LocalDatabaseKudaGo): LocalRepository = LocalRepositoryImpl(database)

    @Singleton
    @Provides
    fun provideInternetRepository(
        service: KudaGoService,
        database: LocalDatabaseKudaGo,
    ): InternetRepository = InternetRepositoryImpl(service, database)
}
