package com.myapps.pruebaandroidtl.moviesfeature.domain.usecase

import com.myapps.pruebaandroidtl.moviesfeature.data.datasource.MoviesDataSource
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.PopularMoviesModel
import com.myapps.pruebaandroidtl.utils.StateResult

class FakeDataSource: MoviesDataSource {
    override suspend fun getPopularMovies(): StateResult<PopularMoviesModel?> {
        TODO("Not yet implemented")
    }
}