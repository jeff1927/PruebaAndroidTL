package com.myapps.pruebaandroidtl.moviesfeature.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.usecase.GetAllMoviesUseCase
import com.myapps.pruebaandroidtl.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase
) : ViewModel() {
    private val _moviesList = MutableLiveData<Resource<List<MovieModel>>>()
    val moviesList: LiveData<Resource<List<MovieModel>>> = _moviesList

    init {
        getMovies()
    }

    fun getMovies() = viewModelScope.launch(Dispatchers.IO) {
        getAllMoviesUseCase().collect {
            _moviesList.postValue(it)
        }
    }
}
