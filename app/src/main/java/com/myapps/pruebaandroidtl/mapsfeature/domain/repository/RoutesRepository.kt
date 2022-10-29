package com.myapps.pruebaandroidtl.mapsfeature.domain.repository

import com.google.android.gms.maps.model.LatLng
import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel
import com.myapps.pruebaandroidtl.utils.StateResult

interface RoutesRepository {

    suspend fun getRoutes(start: LatLng, end:LatLng): StateResult<RouteModel>
}