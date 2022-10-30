package com.myapps.pruebaandroidtl.moviesfeature.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel

@Database(entities = [MovieModel::class], version = 1)
abstract class MoviesDataBase : RoomDatabase(){

    companion object {
        fun getInstance(context: Context): MoviesDataBase {
            return Room.databaseBuilder(context, MoviesDataBase::class.java, "movies").build()
        }
    }

    abstract fun getMoviesDao(): MoviesDao
}