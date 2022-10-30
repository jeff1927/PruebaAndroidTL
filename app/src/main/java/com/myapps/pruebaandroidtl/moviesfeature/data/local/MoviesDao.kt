package com.myapps.pruebaandroidtl.moviesfeature.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMovies(movies: List<MovieModel>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieModel>>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()

}