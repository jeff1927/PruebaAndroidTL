package com.myapps.pruebaandroidtl.moviesfeature.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myapps.pruebaandroidtl.mapsfeature.data.datasource.RoutesDataSource
import com.myapps.pruebaandroidtl.mapsfeature.data.datasource.RoutesDataSourceImp
import com.myapps.pruebaandroidtl.mapsfeature.data.repositoryimp.RoutesRepositoryImpl
import com.myapps.pruebaandroidtl.mapsfeature.domain.repository.RoutesRepository
import com.myapps.pruebaandroidtl.moviesfeature.data.api.TheMovieDataBaseApi
import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSource
import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSourceImpl
import com.myapps.pruebaandroidtl.moviesfeature.data.local.MoviesDao
import com.myapps.pruebaandroidtl.moviesfeature.data.repositoryimp.MoviesRepositoryImpl
import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import com.myapps.pruebaandroidtl.utils.constants.MOVIES_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoviesBaseUrl(): String {
        return MOVIES_BASE_URL
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    @Singleton
    @Provides
    fun provideConvertFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }

    @Singleton
    @Provides
    fun provideClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    fun provideRetrofit(
        baseUrl: String,
        converterFactory: Converter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesApiService(retrofit: Retrofit): TheMovieDataBaseApi {
        return retrofit.create(TheMovieDataBaseApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesDataSource(apiService: TheMovieDataBaseApi): MoviesDataSource {
        return MoviesDataSourceImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(moviesDataSource: MoviesDataSource, dao: MoviesDao): MoviesRepository {
        return MoviesRepositoryImpl(moviesDataSource, dao)
    }

    @Singleton
    @Provides
    fun provideRoutesDataSource(apiService: TheMovieDataBaseApi): RoutesDataSource {
        return RoutesDataSourceImp(apiService)
    }

    @Singleton
    @Provides
    fun provideRoutesRepository(dataSource: RoutesDataSource): RoutesRepository{
        return RoutesRepositoryImpl(dataSource)
    }

}