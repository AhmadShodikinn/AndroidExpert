package com.dicoding.animax.core.di

import com.dicoding.core.data.repository.AnimeRepository
import com.dicoding.core.data.source.local.LocalDataSource
import com.dicoding.core.data.source.remote.RemoteDataSource
import com.dicoding.core.data.source.remote.network.ApiService
import com.dicoding.core.domain.repository.IAnimeRepository
import com.dicoding.core.domain.usecase.AnimeInteractor
import com.dicoding.core.domain.usecase.AnimeUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: ApiService): RemoteDataSource {
        return RemoteDataSource(apiService)
    }

    @Provides
    @Singleton
    fun provideAnimeRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource
    ): IAnimeRepository {
        return AnimeRepository(
            remoteDataSource,
            localDataSource
        )
    }

    @Provides
    @Singleton
    fun provideAnimeUseCase(
        animeRepository: IAnimeRepository
    ): AnimeUseCase {
        return AnimeInteractor(animeRepository)
    }
}