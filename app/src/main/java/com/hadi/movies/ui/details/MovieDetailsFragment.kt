package com.hadi.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.hadi.movies.databinding.ActivityMovieDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private var _binding: ActivityMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val movieDetailsViewModel by viewModels<MovieDetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.movieViewModel = movieDetailsViewModel
        return binding.root
    }

}