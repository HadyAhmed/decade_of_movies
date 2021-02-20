package com.hadi.movies.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadi.movies.data.model.local.Movie
import com.hadi.movies.data.repository.MoviesRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieListViewModel @ViewModelInject constructor(private val repository: MoviesRepository) :
    ViewModel() {

    val movieTitle = MutableLiveData<String>()
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    init {
        searchForMovie()
    }

    fun searchForMovie(movieName: String? = null) = repository.search(movieName).onEach {
        _movies.value = it
    }.launchIn(viewModelScope)
}