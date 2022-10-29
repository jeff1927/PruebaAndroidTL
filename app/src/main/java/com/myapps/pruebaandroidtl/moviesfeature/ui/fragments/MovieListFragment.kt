package com.myapps.pruebaandroidtl.moviesfeature.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.myapps.pruebaandroidtl.databinding.FragmentMovieListBinding
import com.myapps.pruebaandroidtl.moviesfeature.ui.adapters.MoviesAdapter
import com.myapps.pruebaandroidtl.moviesfeature.ui.viewmodels.MoviesViewModel
import com.myapps.pruebaandroidtl.utils.StateResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : Fragment() {

    private val viewModel by viewModels<MoviesViewModel>()

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObserver()
    }

    private fun setupRecyclerView() {
        binding.rvMovies.apply { 
            layoutManager = LinearLayoutManager(requireContext())
            adapter = MoviesAdapter(setOnItemClicked = {
                findNavController().navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieDetailFragment(it))
            })
        }
    }
    private fun setupObserver() {
        viewModel.moviesList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is StateResult.Success -> {
                    binding.rvMovies.adapter?.let {
                        response.data?.let { pagingData ->
                            (it as MoviesAdapter).submitData(lifecycle, pagingData)
                        }
                    }
                }
                is StateResult.Failed -> {
                    response.code.let { message ->
                        Toast.makeText(
                            requireContext(),
                            "AN_ERROR_HAS_OCCURRED $message",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

}