package com.example.data.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): LocalDatabaseKudaGo =
        Room
            .databaseBuilder(
                context,
                LocalDatabaseKudaGo::class.java,
                "local_data_base_kuga_go",
            )
            .allowMainThreadQueries()
            .build()
}
