package com.hadi.movies.ui.details

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.hadi.movies.Constants
import com.hadi.movies.data.model.PhotosResponse
import com.hadi.movies.data.model.local.Movie
import com.hadi.movies.data.network.Resource
import com.hadi.movies.data.repository.MoviesRepository
import com.hadi.movies.utils.CallbackAction
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MovieDetailsViewModel @ViewModelInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : ViewModel() {

    private val _movieDetails = MutableLiveData<Movie>()
    val movieDetails: LiveData<Movie> get() = _movieDetails

    private val _moviePhotos = MutableLiveData<Resource<PhotosResponse>>()
    val moviePhotos: LiveData<Resource<PhotosResponse>> get() = _moviePhotos

    val retry = CallbackAction {
        savedStateHandle.get<Int>(Constants.MOVIE_ID)?.let {
            fetchMovieDetails(it)
        }
    }

    init {
        retry.sendCallBack()
    }

    private fun fetchMovieDetails(movieId: Int) {
        repository.fetchMovieDetails(movieId)
            .onEach {
                _movieDetails.value = it
                callFlickr(it)
            }.launchIn(viewModelScope)
    }

    private fun callFlickr(it: Movie) {
        repository.fetchMovieFlickerPhotos(it.title).onEach {
            _moviePhotos.value = it
        }.launchIn(viewModelScope)
    }
}