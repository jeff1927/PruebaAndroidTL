package com.myapps.pruebaandroidtl.mapsfeature.di

import com.myapps.pruebaandroidtl.mapsfeature.domain.repository.RoutesRepository
import com.myapps.pruebaandroidtl.mapsfeature.domain.usecases.GetRoutesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MapsDomainModule {

    @Singleton
    @Provides
    fun providesGetRoutesUseCase(repository: RoutesRepository): GetRoutesUseCase{
        return GetRoutesUseCase(repository)
    }
}