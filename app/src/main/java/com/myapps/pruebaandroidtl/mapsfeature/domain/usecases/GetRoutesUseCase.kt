package com.myapps.pruebaandroidtl.mapsfeature.domain.usecases

import com.google.android.gms.maps.model.LatLng
import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel
import com.myapps.pruebaandroidtl.mapsfeature.domain.repository.RoutesRepository
import com.myapps.pruebaandroidtl.utils.StateResult
import javax.inject.Inject

class GetRoutesUseCase @Inject constructor(private val routesRepository: RoutesRepository) {

    suspend operator fun invoke(start: LatLng, end:LatLng): StateResult<RouteModel>{
        return routesRepository.getRoutes(start, end)
    }
}