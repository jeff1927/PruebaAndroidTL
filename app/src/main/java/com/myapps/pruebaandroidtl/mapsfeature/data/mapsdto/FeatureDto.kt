package com.myapps.pruebaandroidtl.mapsfeature.data.mapsdto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel

data class FeatureDto(
    @Expose
    @SerializedName("geometry")
    val geometry: GeometryDto,
    @Expose
    @SerializedName("properties")
    val properties: PropertiesDto,
    ){
    fun  toDomainModel() = RouteModel(
        properties.summary.distance,
        properties.summary.duration,
        geometry.coordinates
    )
}
