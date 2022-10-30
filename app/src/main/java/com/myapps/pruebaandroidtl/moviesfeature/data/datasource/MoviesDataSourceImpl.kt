package com.myapps.pruebaandroidtl.moviesfeature.data.datasource

import com.myapps.pruebaandroidtl.moviesfeature.data.api.TheMovieDataBaseApi
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.PopularMoviesModel
import com.myapps.pruebaandroidtl.utils.StateResult
import java.lang.Exception
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(private val apiService: TheMovieDataBaseApi) : MoviesDataSource{
    override suspend fun getPopularMovies(): StateResult<PopularMoviesModel?> {
        try {
            val data = apiService.getPopularMovies()
            if (data.isSuccessful){
                return StateResult.Success(data.body()?.toDomainModel())
            }
        }catch (e: Exception){
            throw (e)
        }
        return StateResult.Failed("Error Desconocido")
    }
}
