package com.myapps.pruebaandroidtl.moviesfeature.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel
import com.myapps.pruebaandroidtl.moviesfeature.domain.usecase.GetAllMoviesUseCase
import com.myapps.pruebaandroidtl.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val getAllMoviesUseCase: GetAllMoviesUseCase
): ViewModel() {
    private val _moviesList = MutableLiveData<StateResult<PagingData<MovieModel>>>()
    val moviesList: LiveData<StateResult<PagingData<MovieModel>>> = _moviesList
    init {
        getMoviesPageList()
    }

    private fun getMoviesPageList() {
        viewModelScope.launch {
            getAllMoviesUseCase().cachedIn(viewModelScope)
                .catch {
                    _moviesList.postValue(StateResult.Failed(this.toString()))
                }
                .collect {
                    _moviesList.postValue(StateResult.Success(it))
                }
        }
    }
}