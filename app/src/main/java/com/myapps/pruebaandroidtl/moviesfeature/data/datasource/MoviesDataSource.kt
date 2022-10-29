package com.myapps.pruebaandroidtl.moviesfeature.data.datasource


import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.PopularMoviesModel
import com.myapps.pruebaandroidtl.utils.StateResult

interface MoviesDataSource {

    suspend fun getPopularMovies(page: Int): StateResult<PopularMoviesModel?>

    suspend fun getPopularMoviesNotNull(page: Int): List<MovieModel>
}