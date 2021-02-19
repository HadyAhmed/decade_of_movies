package com.hadi.movies.data.model.local

import com.google.gson.annotations.SerializedName

data class MoviesFile(
    @SerializedName("movies")
    val movies: List<Movie>
)