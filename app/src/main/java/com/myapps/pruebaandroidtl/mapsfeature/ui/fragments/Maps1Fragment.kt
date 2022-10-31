package com.myapps.pruebaandroidtl.mapsfeature.ui.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.myapps.pruebaandroidtl.utils.constants.CUSTOM_ZOOM
import com.myapps.pruebaandroidtl.utils.constants.ONE_INT
import com.myapps.pruebaandroidtl.utils.constants.ROUTE_LINE_WIDTH
import com.myapps.pruebaandroidtl.utils.constants.ZERO_INT
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Maps1Fragment : Fragment() {

    private var map: GoogleMap? = null

    private lateinit var geocoder: Geocoder

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var mRoute: RouteModel? = null

    private var polyLineOptions: PolylineOptions? = null

    private var mPolyLine: Polyline? = null

    private val viewModel by viewModels<MapsViewModel>()

    companion object{
        const val  REQUEST_CODE_LOCATION = 1
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        map?.setOnMapClickListener { location ->
            viewModel.saveEndLocation(location)
            map?.clear()
            drawMyLocationMarker()
            drawEndLocationMarker()
            createRoute()
            drawPolyLine()
        }
        locationPermission()
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
        drawEndLocationMarker()
    }

    private fun setupListeners() {
        binding.btnSeeRouteInfo.setOnClickListener {
            mRoute?.let {
                findNavController().navigate(Maps1FragmentDirections.actionMaps1FragmentToRouteInfoFragment(
                    floatArrayOf(it.distance.toFloat(), it.duration.toFloat())))
            }
        }
    }

    private fun setupObservers() {
        viewModel.route.observe(viewLifecycleOwner){ route ->
            mRoute = route.data
            when(route){
                is StateResult.Success -> {
                    polyLineOptions= PolylineOptions()
                    route.data?.coordinates?.forEach {
                        polyLineOptions?.add(LatLng(it[ONE_INT], it[ZERO_INT]))
                    }
                    polyLineOptions?.let {
                        it
                            .width(ROUTE_LINE_WIDTH)
                            .color(ContextCompat.getColor(requireContext(),R.color.purple_700))
                        viewModel.savePolyLineOptions(it)
                    }
                }
                else -> {
                    Toast.makeText(requireContext(),getString(R.string.route_not_found), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun drawPolyLine() {
        viewModel.polylineOptions.observe(viewLifecycleOwner){
            it?.let {poly ->
                removeOldPolyLine()
                mPolyLine = map?.addPolyline(poly)
            }
        }
    }

    private fun removeOldPolyLine() {
        mPolyLine?.remove()
        mPolyLine = null
    }

    private fun drawMyLocationMarker() {
        viewModel.startLocation.observe(viewLifecycleOwner) { myLocation ->
            myLocation?.let {
                drawNewMarker(it)
                val address = getAddress(it)
                binding.tvMyLocationAddress.text = getString(R.string.label_from, address)
            }
        }
    }

    private fun drawEndLocationMarker() {
        viewModel.endLocation.observe(viewLifecycleOwner){ myLocation ->
            binding.btnSeeRouteInfo.isEnabled = true
            myLocation?.let {
                drawNewMarker(it)
                val address = getAddress(it)
                binding.tvTargetLocationAddress.text = getString(R.string.label_to, address)
            }
        }
    }

    private fun drawNewMarker(location: LatLng) {
        val address = getAddress(location)
        val marker = MarkerOptions().position(location).title(address)
        map?.addMarker(marker)
    }

    private fun getAddress(location: LatLng?): String {
        location?.let {
            return geocoder.getFromLocation(it.latitude, it.longitude, ONE_INT)[ZERO_INT].getAddressLine(ZERO_INT)
        } ?: return getString(R.string.wrong_location)
    }

    private fun createRoute() {
        viewModel.startLocation.observe(viewLifecycleOwner) { itStart ->
            itStart?.let { startLocation ->
                viewModel.endLocation.observe(viewLifecycleOwner) { itEnd ->
                    itEnd?.let { endLocation ->
                        viewModel.getRoute(startLocation, endLocation)
                    }
                }
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
                getString(R.string.go_to_settings),
                Toast.LENGTH_SHORT
            ).show()
        }
        createRoute()
        drawPolyLine()
    }


    private fun locationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map?.isMyLocationEnabled = true
            fusedLocationProviderClient.lastLocation.addOnSuccessListener(requireActivity()) {
                val location = LatLng(it.latitude,it.longitude)
                setMyLocationOnMap(location)
            }
            return
        }else {
            requestLocationPermission()
        }
    }

    private fun setMyLocationOnMap(location: LatLng?) {
        location?.let{
            viewModel.saveStartPoint(it)
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(it, CUSTOM_ZOOM),null)
            drawMyLocationMarker()
            drawEndLocationMarker()
        }
    }

    private fun requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireContext(),getString(R.string.go_to_settings),Toast.LENGTH_SHORT).show()
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