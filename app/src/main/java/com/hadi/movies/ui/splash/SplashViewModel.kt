package com.hadi.movies.ui.splash

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hadi.movies.data.repository.MoviesRepository
import kotlinx.coroutines.launch

class SplashViewModel @ViewModelInject constructor(private val repository: MoviesRepository) :
    ViewModel() {

    private val _startApplication = MutableLiveData<Boolean>()
    val startApplication : LiveData<Boolean> get()=_startApplication

    init {
        viewModelScope.launch {
            repository.insertMovies()
            _startApplication.value = true
        }
    }
}