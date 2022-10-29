package com.myapps.pruebaandroidtl.moviesfeature.data.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel

data class ResultDto(
//    val adult: Boolean,
//    val backdrop_path: String,
//    val genre_ids: List<Int>,
    @Expose
    @SerializedName("id")
    val id: Int,
//    val original_language: String,
    @Expose
    @SerializedName("original_title")
    val original_title: String,
    @Expose
    @SerializedName("overview")
    val overview: String,
//    val popularity: Double,
    @Expose
    @SerializedName("poster_path")
    val poster_path: String,
    @Expose
    @SerializedName("release_date")
    val release_date: String,
    @Expose
    @SerializedName("title")
    val title: String,
//    val video: Boolean,
//    val vote_average: Double,
//    val vote_count: Int
){
    fun toDomainModel() = MovieModel(
        id,
        original_title,
        overview,
        poster_path,
        release_date,
        title
    )
}