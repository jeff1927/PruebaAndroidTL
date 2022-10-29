package com.myapps.pruebaandroidtl.mapsfeature.domain.models

data class RouteModel (
    val distance: Double,
    val duration: Double,
    val coordinates: List<List<Double>>
    )