package com.myapps.pruebaandroidtl.moviesfeature.di

import android.content.Context
import com.myapps.pruebaandroidtl.moviesfeature.data.local.MoviesDao
import com.myapps.pruebaandroidtl.moviesfeature.data.local.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): MoviesDataBase{
        return MoviesDataBase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideMoviesDao(dataBase: MoviesDataBase): MoviesDao {
        return dataBase.getMoviesDao()
    }
}