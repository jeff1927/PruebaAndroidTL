package com.myapps.pruebaandroidtl.moviesfeature.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSource
import com.myapps.pruebaandroidtl.moviesfeature.data.local.MoviesDao
import com.myapps.pruebaandroidtl.utils.constants.INITIAL_PAGE
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

@ExperimentalPagingApi
class MoviesRemoteMediator2 @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesDataSource: MoviesDataSource,
    private val initialKey: Int = INITIAL_PAGE
) : RemoteMediator<Int, MovieModel>(){

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }


    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, MovieModel>
    ): MediatorResult {
        val pageKeyData = getKeyPageData(loadType, state)
        val page = when (pageKeyData) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        try {
            val response = moviesDataSource.getPopularMoviesNotNull(page)
            val isEndOfList = response.isEmpty()

                if (loadType == LoadType.REFRESH) {
                    moviesDao.deleteAllItems()
                    moviesDao.deleteAllMoviesKey()
                }
                val prevKey = if (page == initialKey) null else page - 1
                val nextKey = if (isEndOfList) null else page + 1
                val keys = response.map {
                    MoviesKey(it.title, prevKey, nextKey)
                }
                moviesDao.insertAllMoviesKeys(keys)
                moviesDao.insertAll(response)

            return MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, MovieModel>): Any {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.next?.minus(1) ?: initialKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                val nextKey = remoteKeys?.next
                return nextKey ?: MediatorResult.Success(endOfPaginationReached = false)
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                val prevKey = remoteKeys?.prev ?: return MediatorResult.Success(
                    endOfPaginationReached = false
                )
                prevKey
            }
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MovieModel>): MoviesKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { repoId ->
                moviesDao.getAllKeys(repoId.toString())
            }
        }
    }

    private suspend fun getLastRemoteKey(state: PagingState<Int, MovieModel>): MoviesKey? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { movie -> moviesDao.getAllKeys(movie.id.toString()) }
    }

    private suspend fun getFirstRemoteKey(state: PagingState<Int, MovieModel>): MoviesKey? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { movie -> moviesDao.getAllKeys(movie.id.toString()) }
    }
}