package com.myapps.pruebaandroidtl.moviesfeature.data.repositoryimp

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSource
import com.myapps.pruebaandroidtl.moviesfeature.data.local.MoviesDao
import com.myapps.pruebaandroidtl.moviesfeature.data.paging.MoviesRemoteMediator2
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.repository.MoviesRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class MoviesRepositoryImpl @Inject constructor(
    private val moviesDataSource: MoviesDataSource,
    private val moviesDao: MoviesDao
) : MoviesRepository{
    @OptIn(ExperimentalPagingApi::class)
    override suspend fun getPopularMovies(): Flow<PagingData<MovieModel>> {
        val pagingSourceFactory = {moviesDao.getAllMovies()}
        return Pager(
            config = PagingConfig(10, prefetchDistance = 5),
            remoteMediator = MoviesRemoteMediator2(moviesDao, moviesDataSource),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }


}