package com.myapps.pruebaandroidtl.moviesfeature.data.api

import com.myapps.pruebaandroidtl.mapsfeature.data.mapsdto.RouteDto
import com.myapps.pruebaandroidtl.moviesfeature.data.dto.PopularMoviesDto
import com.myapps.pruebaandroidtl.utils.constants.ONE_INT
import com.myapps.pruebaandroidtl.utils.constants.POPULAR_MOVIES_ENDPOINT
import com.myapps.pruebaandroidtl.utils.constants.TMDB_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface TheMovieDataBaseApi {

    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun getPopularMovies(
        @Query("api_key")
        api_key: String = TMDB_API_KEY,
        @Query("page")
        page: Int = ONE_INT
    ) : Response<PopularMoviesDto>

    @GET(POPULAR_MOVIES_ENDPOINT)
    suspend fun getPopularMoviesNotNull(
        @Query("api_key")
        api_key: String = TMDB_API_KEY,
        @Query("page")
        page: Int = ONE_INT
    ) : PopularMoviesDto

    @GET
    suspend fun getRoutes(
        @Url
        url: String
    ) : Response<RouteDto>
}