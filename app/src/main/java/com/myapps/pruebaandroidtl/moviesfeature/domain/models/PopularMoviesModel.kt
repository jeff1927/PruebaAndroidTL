package com.myapps.pruebaandroidtl.moviesfeature.domain.models

data class PopularMoviesModel(
    val page: Int,
    val movies: List<MovieModel>,
    val total_pages: Int,
    val total_results: Int
)
