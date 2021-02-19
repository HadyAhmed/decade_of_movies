package com.hadi.movies.data.repository

import android.content.Context
import androidx.room.withTransaction
import com.google.gson.Gson
import com.hadi.movies.data.db.manager.MoviesDatabase
import com.hadi.movies.data.model.PhotosResponse
import com.hadi.movies.data.model.local.Movie
import com.hadi.movies.data.model.local.MoviesFile
import com.hadi.movies.data.network.MoviesApiService
import com.hadi.movies.data.network.NetworkManager
import com.hadi.movies.data.network.Resource
import com.hadi.movies.utils.readAssetsFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MoviesRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson,
    private val api: MoviesApiService,
    private val networkManager: NetworkManager,
    private val database: MoviesDatabase
) {

    suspend fun insertMovies() {
        database.withTransaction {
            database.movieDao().apply {
                clearDatabase()
                insertMovies(*readMoviesFromAssets().movies.toTypedArray())
            }
        }
    }

    fun search(title: String? = null): Flow<List<Movie>> =
        database.movieDao().searchForMovies("%$title%")

    fun fetchMovieDetails(movieId: Int): Flow<Movie> =
        database.movieDao().fetchMovieDetails(movieId)

    fun searchForMovie(title: String): Flow<Resource<PhotosResponse>> = flow {
        if (networkManager.isNetworkConnected()) {
            emit(Resource.Loading())
            try {
                val response = api.fetchMovie(title = title)
                if (response.stat == "ok") {
                    if (response.photos.photo.isNullOrEmpty()) {
                        emit(Resource.Empty<PhotosResponse>())
                    } else {
                        emit(Resource.Success(response))
                    }
                } else {
                    emit(Resource.Error<PhotosResponse>())
                }
            } catch (exception: IOException) {
                emit(Resource.Failed<PhotosResponse>(message = exception.message))
            } catch (exception: HttpException) {
                emit(Resource.Error<PhotosResponse>(message = exception.message()))
            }
        } else {
            emit(Resource.NoNetwork<PhotosResponse>())
        }
    }

    private fun readMoviesFromAssets(): MoviesFile = gson.fromJson(
        context.assets.readAssetsFile("movies.json"),
        MoviesFile::class.java
    )
}