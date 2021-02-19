package com.hadi.movies.ui.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadi.movies.Constants
import com.hadi.movies.data.model.local.Movie
import com.hadi.movies.data.repository.MoviesRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieDetailsViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> get() = _movieDetails

    init {
        savedStateHandle.get<Int>(Constants.MOVIE_ID)?.let {
            fetchMovieDetails(it)
        }
    }

    private fun fetchMovieDetails(movieId: Int) {
        repository.fetchMovieDetails(movieId).onEach {
            _movieDetails.value = it
        }.launchIn(viewModelScope)
    }
}