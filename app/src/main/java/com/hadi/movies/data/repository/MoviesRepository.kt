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
import com.hadi.movies.data.network.Status
import com.hadi.movies.ui.details.MovieDetailsActivity
import com.hadi.movies.ui.list.MovieListActivity
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

    /**
     * inserting movies inside room
     * this method will clear all database and reinsert all movies from json file
     * TODO: refactor this behavior for real data source
     */
    suspend fun insertMovies() {
        database.withTransaction {
            database.movieDao().apply {
                clearDatabase()
                insertMovies(*readMoviesFromAssets().movies.toTypedArray())
            }
        }
    }

    /**
     * this method will retrieve all movies from room in case the input text was empty
     * and return 5 movies categorized with year sorting when the user insert title matches movie
     * @param title the movie title inserted by the user
     */
    fun search(title: String? = null): Flow<List<Movie>> {
        return if (title.isNullOrEmpty()) {
            database.movieDao().showAllMovies()
        } else {
            database.movieDao().searchForMovies("%$title%")
        }
    }

    /**
     * fetching movie details from room with id
     * @param movieId the movie id passed when the user clicks on movie
     * @see MovieListActivity
     * @see MovieDetailsActivity
     */
    fun fetchMovieDetails(movieId: Int): Flow<Movie> =
        database.movieDao().fetchMovieDetails(movieId)

    /**
     * this method will fetch movie's title match photos from flicker
     * and return with results [Empty, Photos, No Internet Connection, No Photos]
     * @see Resource
     * @see Status
     */
    fun fetchMovieFlickerPhotos(title: String): Flow<Resource<PhotosResponse>> = flow {
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

    /**
     * reads a json file from assets and return a variable with type MovieFile
     * TODO: Refactor this to be able to read any file name and return any variable type
     */
    private fun readMoviesFromAssets(): MoviesFile = gson.fromJson(
        context.assets.readAssetsFile("movies.json"),
        MoviesFile::class.java
    )
}