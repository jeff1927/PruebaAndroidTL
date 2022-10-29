package com.myapps.pruebaandroidtl.moviesfeature.domain.repository

import androidx.paging.PagingData
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMovies(): Flow<PagingData<MovieModel>>
}