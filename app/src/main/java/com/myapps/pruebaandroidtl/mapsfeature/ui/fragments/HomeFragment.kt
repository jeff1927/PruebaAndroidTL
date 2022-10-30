package com.myapps.pruebaandroidtl.mapsfeature.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myapps.pruebaandroidtl.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() {
        with(binding){
            btnMaps.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMaps1Fragment())
            }
            btnMovies.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToMovieListFragment())
            }
        }
    }

}