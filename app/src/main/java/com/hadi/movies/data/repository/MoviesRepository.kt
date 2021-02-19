package com.hadi.movies.data.repository

import com.hadi.movies.data.model.PhotosResponse
import com.hadi.movies.data.network.MoviesApiService
import com.hadi.movies.data.network.NetworkManager
import com.hadi.movies.data.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(
    private val api: MoviesApiService,
    private val networkManager: NetworkManager
) {

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
}