package com.myapps.pruebaandroidtl.moviesfeature.data.datasource

import com.myapps.pruebaandroidtl.moviesfeature.data.api.ApiService
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.PopularMoviesModel
import com.myapps.pruebaandroidtl.utils.StateResult
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    MoviesDataSource {
    override suspend fun getPopularMovies(): StateResult<PopularMoviesModel?> {
        try {
            val data = apiService.getPopularMovies()
            if (data.isSuccessful){
                return StateResult.Success(data.body()?.toDomainModel())
            }
        } catch (e: Exception) {
            throw (e)
        }
        return StateResult.Failed("Error Desconocido")
    }
}
