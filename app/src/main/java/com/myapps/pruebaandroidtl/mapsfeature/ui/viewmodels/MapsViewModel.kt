package com.myapps.pruebaandroidtl.mapsfeature.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel
import com.myapps.pruebaandroidtl.mapsfeature.domain.usecases.GetRoutesUseCase
import com.myapps.pruebaandroidtl.utils.StateResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class MapsViewModel @Inject constructor(private val getRoutesUseCase: GetRoutesUseCase): ViewModel() {

    private val _route = MutableLiveData<StateResult<RouteModel>>()
    val route: LiveData<StateResult<RouteModel>> = _route

    fun getRoute(start:LatLng, end: LatLng){
        viewModelScope.launch (Dispatchers.IO){
            getRoutesUseCase(start, end).let {
                _route.postValue(it)
            }
        }
    }


}