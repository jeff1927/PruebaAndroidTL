package com.myapps.pruebaandroidtl.moviesfeature.ui.viewmodels

import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import com.myapps.pruebaandroidtl.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

val mockMoviesList = listOf<MovieModel>(
    MovieModel(1234, "superman", "superman is the best hero","path1", "00/00/000", "SUPERMAN"),
    MovieModel(1234, "superman", "superman is the best hero","path1", "00/00/000", "SUPERMAN"),
    MovieModel(1234, "superman", "superman is the best hero","path1", "00/00/000", "SUPERMAN"),
    MovieModel(1234, "superman", "superman is the best hero","path1", "00/00/000", "SUPERMAN"),
    MovieModel(1234, "superman", "superman is the best hero","path1", "00/00/000", "SUPERMAN")
)

class FakeRepository : MoviesRepository {

    override suspend fun getMovies(): Flow<Resource<List<MovieModel>>> {
        return flowOf(Resource.Success(mockMoviesList))
    }
}