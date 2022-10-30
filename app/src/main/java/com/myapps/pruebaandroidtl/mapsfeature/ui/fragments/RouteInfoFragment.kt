package com.myapps.pruebaandroidtl.mapsfeature.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.myapps.pruebaandroidtl.R
import com.myapps.pruebaandroidtl.databinding.FragmentRouteInfoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteInfoFragment : Fragment() {

    private val safeArgs: RouteInfoFragmentArgs? by navArgs()

    private var _binding: FragmentRouteInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRouteInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        with(binding){
            safeArgs?.let {
                val distance = String.format("%.1f",it.routeInfo?.get(0)?.div(1000))
                tvDistance.text = getString(R.string.label_distance, distance.toString())
                val time = it.routeInfo?.get(1)?.div(60)
                tvTime.text = getString(R.string.label_time, String.format("%.1f", time))
            }

        }
    }

}