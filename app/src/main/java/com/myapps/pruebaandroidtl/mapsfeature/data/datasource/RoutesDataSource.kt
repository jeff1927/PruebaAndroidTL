package com.myapps.pruebaandroidtl.mapsfeature.data.datasource

import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel
import com.myapps.pruebaandroidtl.utils.StateResult

interface RoutesDataSource {

    suspend fun getRoute(query: String): StateResult<RouteModel>

}