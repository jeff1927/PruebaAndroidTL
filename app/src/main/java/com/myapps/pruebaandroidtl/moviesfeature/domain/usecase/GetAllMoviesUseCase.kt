package com.myapps.pruebaandroidtl.moviesfeature.domain.usecase

import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import com.myapps.pruebaandroidtl.utils.Resource
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllMoviesUseCase @Inject constructor(private val repository: MoviesRepository) {
    suspend operator fun invoke(): Flow<Resource<List<MovieModel>>> {
        return repository.getMovies()
    }
}
