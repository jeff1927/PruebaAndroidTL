package com.myapps.pruebaandroidtl.moviesfeature.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSource
import com.myapps.pruebaandroidtl.moviesfeature.data.local.MoviesDao
import com.myapps.pruebaandroidtl.utils.constants.ONE_INT
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.utils.StateResult
import javax.inject.Inject


@ExperimentalPagingApi
class MoviesRemoteMediator @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesDataSource: MoviesDataSource,
    private val initialKey: Int = ONE_INT
    ) : RemoteMediator<Int, MovieModel>(){
    override suspend fun load(loadType: LoadType, state: PagingState<Int, MovieModel>): MediatorResult {
    return try {
            val page: Int = when(loadType){
                LoadType.APPEND -> {
                    val remoteKeys = getLastKey(state)
                    remoteKeys?.next ?: return MediatorResult.Success(true)
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(true)
                }
                LoadType.REFRESH -> {
                    val remoteKeys = getClosestKey(state)
                    remoteKeys?.next?.minus(1) ?: initialKey
                }
            }
            val response = moviesDataSource.getPopularMovies(page)
            val endOfPagination = response.data?.movies?.size!! < state.config.pageSize

            when (response){
                is StateResult.Success -> {
                    if (loadType == LoadType.REFRESH){
                        moviesDao.deleteAllItems()
                        moviesDao.deleteAllMoviesKey()
                    }

                    val prev = if (page == initialKey) null else page.minus(1)
                    val next = if(endOfPagination) null else page.plus(1)

                    response.data.let {data ->
                        val keysList = response.data.movies.map{
                            MoviesKey(it.id.toString(), prev, next)
                        }
                        keysList.let {
                            moviesDao.insertAllMoviesKeys(it)
                        }
                        data.movies.let {
                            moviesDao.insertAll(it)
                        }
                        return MediatorResult.Success(endOfPagination)
                    }

                }
                is StateResult.Failed -> {
                    MediatorResult.Error(Exception())
                }
            }

        }catch (e: Exception){
            MediatorResult.Error(e)
        }


    }

    suspend fun getLastKey(state: PagingState<Int, MovieModel>): MoviesKey? {
        return state.lastItemOrNull()?.let {
            moviesDao.getAllKeys(it.id.toString())
        }
    }

    suspend fun getClosestKey(state: PagingState<Int, MovieModel>): MoviesKey? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestItemToPosition(anchorPosition)?.let {
                moviesDao.getAllKeys(it.id.toString())
            }
        }
    }
}