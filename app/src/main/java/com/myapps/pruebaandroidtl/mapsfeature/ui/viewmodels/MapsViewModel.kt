package com.myapps.pruebaandroidtl.mapsfeature.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
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

    private val _startLocation = MutableLiveData<LatLng?>()
    val startLocation: LiveData<LatLng?> = _startLocation

    private val _endLocation = MutableLiveData<LatLng?>()
    val endLocation: LiveData<LatLng?> = _endLocation

    private val _newRouteLine = MutableLiveData<Polyline?>()
    val newRouteLine: LiveData<Polyline?> = _newRouteLine

    private val _oldRouteLine = MutableLiveData<Polyline?>()
    val oldRouteLine: LiveData<Polyline?> = _oldRouteLine

    private val _polyLineOptions = MutableLiveData<PolylineOptions?>()
    val polylineOptions: LiveData<PolylineOptions?> = _polyLineOptions

    fun getRoute(start:LatLng, end: LatLng) {
        viewModelScope.launch (Dispatchers.IO) {
            getRoutesUseCase(start, end).let {
                _route.postValue(it)
            }
        }
    }

    fun saveStartPoint(startLocation: LatLng) {
        _startLocation.value = startLocation
    }

    fun saveEndLocation(endLocation: LatLng) {
        _endLocation.value = endLocation
    }

    fun saveNewRouteLine(poli: Polyline?) {
        _newRouteLine.value = poli
    }

    fun saveOldRouteLine(poli: Polyline?) {
        _oldRouteLine.value = poli
    }

    fun savePolyLineOptions(polylineOptions: PolylineOptions) {
        _polyLineOptions.value = polylineOptions
    }


}