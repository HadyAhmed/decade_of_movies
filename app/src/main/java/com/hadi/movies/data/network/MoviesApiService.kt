package com.hadi.movies.data.network

import com.hadi.movies.data.model.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiService {

    @GET("services/rest")
    suspend fun fetchMovie(
        @Query("method") method: String = "flickr.photos.search",
        @Query("api_key") apiKey: String = "b7e43ddc0c64e5133caef70b13ba3352",
        @Query("format") format: String = "json",
        @Query("nojsoncallback") nojsoncallback: Int = 1,
        @Query("text") title: String,
        @Query("page") page: Int = 1,
        @Query("per_page") pageSize: Int = 10,
    ): PhotosResponse

}