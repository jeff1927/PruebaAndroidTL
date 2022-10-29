package com.myapps.pruebaandroidtl.mapsfeature.data.repositoryimp

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.myapps.pruebaandroidtl.mapsfeature.data.datasource.RoutesDataSource
import com.myapps.pruebaandroidtl.mapsfeature.domain.repository.RoutesRepository
import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel
import com.myapps.pruebaandroidtl.utils.StateResult
import com.myapps.pruebaandroidtl.utils.constants.ROUTES_ENDPOINT
import com.myapps.pruebaandroidtl.utils.constants.ROUTE_SERVICE_BASE_URL
import com.myapps.pruebaandroidtl.utils.constants.RS_API_KEY
import javax.inject.Inject

class RoutesRepositoryImpl @Inject constructor(private val routesDataSource: RoutesDataSource) :
    RoutesRepository {
    override suspend fun getRoutes(start: LatLng, end:LatLng): StateResult<RouteModel> {
        val query = generateRouteQuery(start, end)
        return try {
            routesDataSource.getRoute(query)
        }catch (e:Exception){
            Log.d("repositorio", "aca se estallla")
            StateResult.Failed("Error en la solicitud")
        }
    }

    private fun generateRouteQuery(start: LatLng, end:LatLng): String{
        return ROUTE_SERVICE_BASE_URL +
                ROUTES_ENDPOINT +
                "api_key="+
                RS_API_KEY +
                "&start="+
                start.longitude.toString()+
                ","+
                start.latitude.toString()+
                "&end="+
                end.longitude.toString()+
                ","+
                end.latitude.toString()
    }
}