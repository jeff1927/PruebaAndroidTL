package com.myapps.pruebaandroidtl.mapsfeature.data.mapsdto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GeometryDto(
    @Expose
    @SerializedName("coordinates")
    val coordinates: List<List<Double>>,
)