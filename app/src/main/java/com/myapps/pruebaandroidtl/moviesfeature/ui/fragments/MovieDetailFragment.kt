package com.myapps.pruebaandroidtl.moviesfeature.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.myapps.pruebaandroidtl.R
import com.myapps.pruebaandroidtl.databinding.FragmentMovieDetailBinding
import com.myapps.pruebaandroidtl.databinding.FragmentRouteInfoBinding
import com.myapps.pruebaandroidtl.mapsfeature.ui.fragments.RouteInfoFragmentArgs
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val safeArgs: MovieDetailFragmentArgs by navArgs()

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        val movie = safeArgs.movie
        with(binding){
            Glide.with(this.root).load("https://image.tmdb.org/t/p/w500" + movie.poster_path)
                .into(ivItemImage)
            tvTitle.text = movie.original_title
            tvDescription.text = movie.overview
        }
    }

}