package com.myapps.pruebaandroidtl.moviesfeature.data.datasource


import com.myapps.pruebaandroidtl.moviesfeature.domain.models.PopularMoviesModel
import com.myapps.pruebaandroidtl.utils.StateResult

interface MoviesDataSource {

    suspend fun getPopularMovies(): StateResult<PopularMoviesModel?>

}
