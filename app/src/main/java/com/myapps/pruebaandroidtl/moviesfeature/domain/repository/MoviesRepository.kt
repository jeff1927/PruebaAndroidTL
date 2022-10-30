package com.myapps.pruebaandroidtl.moviesfeature.domain.repository

import androidx.paging.PagingData
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMovies(): Flow<Resource<List<MovieModel>>>
}
