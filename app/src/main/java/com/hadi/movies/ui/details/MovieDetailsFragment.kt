package com.hadi.movies.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hadi.movies.databinding.ActivityMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private var _binding: ActivityMovieDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

}