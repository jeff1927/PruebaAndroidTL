package com.myapps.pruebaandroidtl.moviesfeature.di

import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import com.myapps.pruebaandroidtl.moviesfeature.domain.usecase.GetAllMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun providesGetAllMoviesUseCase(repository: MoviesRepository): GetAllMoviesUseCase {
        return GetAllMoviesUseCase(repository)
    }
}