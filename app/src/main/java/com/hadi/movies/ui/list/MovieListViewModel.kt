package com.hadi.movies.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.hadi.movies.data.repository.MoviesRepository

class MovieListViewModel @ViewModelInject constructor(private val repository: MoviesRepository) :
    ViewModel() {

    fun searchForMovie(movieName: String) = repository.searchForMovie(movieName)
}