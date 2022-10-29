package com.myapps.pruebaandroidtl.mapsfeature.data.api

import com.myapps.pruebaandroidtl.mapsfeature.data.mapsdto.RouteDto
import com.myapps.pruebaandroidtl.utils.constants.ROUTES_ENDPOINT
import com.myapps.pruebaandroidtl.utils.constants.RS_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenRouteServiceApi {

    @GET(ROUTES_ENDPOINT)
    suspend fun getRoute(
        @Query("api_key")
        api_key: String = RS_API_KEY,
        @Query("start")
        start: String,
        @Query("end")
        end: String
    ): Response<RouteDto>
}