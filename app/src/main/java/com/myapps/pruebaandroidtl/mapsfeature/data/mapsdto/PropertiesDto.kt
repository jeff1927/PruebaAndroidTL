package com.myapps.pruebaandroidtl.mapsfeature.data.mapsdto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PropertiesDto(
    @Expose
    @SerializedName("summary")
    val summary: SummaryDto,
)
