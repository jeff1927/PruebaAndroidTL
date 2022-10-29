package com.myapps.pruebaandroidtl.moviesfeature.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myapps.pruebaandroidtl.moviesfeature.data.paging.MoviesKey
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieModel>)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): PagingSource<Int, MovieModel>

    @Query("DELETE FROM movies")
    suspend fun deleteAllItems()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMoviesKeys(keys: List<MoviesKey>)

    @Query("DELETE FROM keys")
    suspend fun deleteAllMoviesKey()

    @Query("SELECT * FROM keys WHERE id=:id")
    suspend fun getAllKeys(id: String): MoviesKey
}