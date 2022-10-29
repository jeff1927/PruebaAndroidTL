package com.myapps.pruebaandroidtl.moviesfeature.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSource
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import java.io.IOException
import javax.inject.Inject
import retrofit2.HttpException

class MoviesPagingSource @Inject constructor(private val dataSource: MoviesDataSource): PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        val page = params.key ?: 1
        return try {
            val response = dataSource.getPopularMoviesNotNull(page)
            LoadResult.Page(
                data = response,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}