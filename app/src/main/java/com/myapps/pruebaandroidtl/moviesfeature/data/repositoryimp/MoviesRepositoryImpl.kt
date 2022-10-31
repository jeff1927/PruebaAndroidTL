package com.myapps.pruebaandroidtl.moviesfeature.data.repositoryimp

import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSource
import com.myapps.pruebaandroidtl.moviesfeature.data.local.MoviesDao
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import com.myapps.pruebaandroidtl.utils.Resource
import com.myapps.pruebaandroidtl.utils.networkBoundResource
import javax.inject.Inject
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl @Inject constructor(
    private val moviesDataSource: MoviesDataSource,
    private val moviesDao: MoviesDao
) : MoviesRepository {

    override suspend fun getMovies(): Flow<Resource<List<MovieModel>>> {
        return networkBoundResource(
            query = {
                moviesDao.getAllMovies()
            },
            fetch = {
                delay(2000)
                moviesDataSource.getPopularMovies()
            },
            saveFetchResult = {
                moviesDao.deleteAllMovies()
                it.data?.let { it1 -> moviesDao.insertAllMovies(it1.movies) }
            }
        )
    }
}
