package com.myapps.pruebaandroidtl.mapsfeature.data.datasource

import android.util.Log
import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel
import com.myapps.pruebaandroidtl.moviesfeature.data.api.ApiService
import com.myapps.pruebaandroidtl.utils.StateResult
import javax.inject.Inject

class RoutesDataSourceImp @Inject constructor(private val apiService: ApiService) :
    RoutesDataSource {
    override suspend fun getRoute(query: String): StateResult<RouteModel> {
        try {
            val data = apiService.getRoutes(query)
            if (data.isSuccessful) {
                data.body()?.let {
                    return StateResult.Success(it.features[0].toDomainModel())
                }
            }
        } catch (e: Exception) {
            Log.d("datasource", "${e.localizedMessage}")
            throw (e)
        }
        return StateResult.Failed("Error Desconocido")
    }
}