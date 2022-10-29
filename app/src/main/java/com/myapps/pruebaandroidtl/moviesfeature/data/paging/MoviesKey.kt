package com.myapps.pruebaandroidtl.moviesfeature.data.paging

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keys")
data class MoviesKey(
    @PrimaryKey
    var id: String,
    var prev: Int?,
    var next: Int?
)
