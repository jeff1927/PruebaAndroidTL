package com.myapps.pruebaandroidtl.moviesfeature.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.PopularMoviesModel

data class PopularMoviesDto(
    @Expose
    @SerializedName("page")
    val page: Int,
    @Expose
    @SerializedName("results")
    val results: List<ResultDto>,
    @Expose
    @SerializedName("total_pages")
    val total_pages: Int,
    @Expose
    @SerializedName("total_results")
    val total_results: Int
){
    fun toDomainModel()= PopularMoviesModel(
        page,
        results.map { it.toDomainModel() },
        total_pages,
        total_results
    )
}

