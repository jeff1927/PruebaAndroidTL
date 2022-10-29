package com.myapps.pruebaandroidtl.mapsfeature.data.mapsdto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SummaryDto(
    @Expose
    @SerializedName("distance")
    val distance: Double,
    @Expose
    @SerializedName("duration")
    val duration: Double
)
