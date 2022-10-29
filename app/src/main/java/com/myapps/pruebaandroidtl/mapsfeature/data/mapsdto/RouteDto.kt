package com.myapps.pruebaandroidtl.mapsfeature.data.mapsdto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RouteDto(
    @Expose
    @SerializedName("features")
    val features: List<FeatureDto>,

)



