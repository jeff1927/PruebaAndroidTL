package com.myapps.pruebaandroidtl.mapsfeature.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.net.RouteInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.myapps.pruebaandroidtl.R
import com.myapps.pruebaandroidtl.databinding.FragmentMaps1Binding
import com.myapps.pruebaandroidtl.mapsfeature.domain.models.RouteModel
import com.myapps.pruebaandroidtl.mapsfeature.ui.viewmodels.MapsViewModel
import com.myapps.pruebaandroidtl.utils.StateResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Maps1Fragment : Fragment() {

    private var map: GoogleMap? = null

    private lateinit var geocoder: Geocoder

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var start: LatLng? = null

    private var end: LatLng? = null

    private var poly: Polyline? = null

    private var mRoute: RouteModel? = null

    private val viewModel by viewModels<MapsViewModel>()

    companion object{
        const val  REQUEST_CODE_LOCATION = 1
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map?.setOnMapClickListener { location ->
            actualizePolyLine()
            end = location
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f),null)
            drawNewMarker(location)
            binding.btnSeeRouteInfo.isEnabled = true
            createRoute()
        }
        locationPermission()
    }

    private fun actualizePolyLine(){
        poly?.remove()
        poly?.let{poly = null}
    }

    private var _binding: FragmentMaps1Binding? = null
    private val binding get() =  _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMaps1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(callback)
        geocoder = Geocoder(requireContext())
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        setupObservers()
        setupListeners()
    }

    private fun setupListeners() {
        binding.btnSeeRouteInfo.setOnClickListener {
            mRoute?.let {
                findNavController().navigate(Maps1FragmentDirections.actionMaps1FragmentToRouteInfoFragment(
                    floatArrayOf(it.distance.toFloat(), it.duration.toFloat())
                ))
            }
        }
    }

    private fun setupObservers() {
        viewModel.route.observe(viewLifecycleOwner){ route ->
            mRoute = route.data
            when(route){
                is StateResult.Success -> {
                    val polyLineOptions = PolylineOptions()
                    route.data?.coordinates?.forEach {
                        polyLineOptions.add(LatLng(it[1], it[0]))
                    }
                    poly = map?.addPolyline(polyLineOptions)
                }
                else -> {
                    Toast.makeText(requireContext(),"no se ha encontrado la ruta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun drawNewMarker(location: LatLng){
        val address = geocoder.getFromLocation(location.latitude, location.longitude,  2)[0].getAddressLine(0)
        binding.tvTargetLocationAddress.text = getString(R.string.label_to, address)
        createMarker(location,address)
        Toast.makeText(requireContext(), address, Toast.LENGTH_SHORT).show()
    }

    private fun createRoute(){
        start?.let {
            end?.let {
                viewModel.getRoute(start as LatLng, end as LatLng)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            map?.isMyLocationEnabled = false
            Toast.makeText(
                requireContext(),
                "Ve a ajustes y acepta los permisos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun createMarker(location: LatLng, address: String) {
        val marker = MarkerOptions().position(location).title(address)
        map?.addMarker(marker)
    }



    private fun locationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map?.isMyLocationEnabled = true
            fusedLocationProviderClient.lastLocation.addOnSuccessListener(requireActivity()) {
                val location = LatLng(it.latitude,it.longitude)
                setMyLocationOnMap(location)
                map?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f),2000,null)
            }
            return
        }else{
            requestLocationPermission()
        }

    }

    private fun setMyLocationOnMap(location: LatLng?){
        location?.let{
            start = it
            val address = geocoder.getFromLocation(location.latitude, location.longitude,1)[0].getAddressLine(0)
            binding.tvMyLocationAddress.text = getString(R.string.label_from, address)
            createMarker(location, address)
        }
    }

    private fun requestLocationPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireContext(),"Ve a ajustes y acepta los permisos",Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE_LOCATION)

        }
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }


}