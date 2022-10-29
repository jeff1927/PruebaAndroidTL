package com.myapps.pruebaandroidtl.moviesfeature.domain.usecase

import androidx.paging.PagingData
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend operator fun invoke(): Flow<PagingData<MovieModel>> {
        return repository.getPopularMovies()
    }
}