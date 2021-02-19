package com.hadi.movies.data.network

enum class Status { LOADING, SUCCESS, ERROR, NO_NETWORK, FAILED, EMPTY }

sealed class Resource<T>(
    val data: T? = null,
    val apiStatus: Status,
    val message: String? = null
) {
    class Success<T>(data: T? = null, message: String? = null) :
        Resource<T>(data = data, apiStatus = Status.SUCCESS, message = message)

    class Loading<T> : Resource<T>(apiStatus = Status.LOADING)
    class Error<T>(message: String? = null, data: T? = null) :
        Resource<T>(apiStatus = Status.ERROR, message = message, data = data)

    class NoNetwork<T>(data: T? = null) : Resource<T>(apiStatus = Status.NO_NETWORK, data = data)

    class Empty<T>(message: String? = null) :
        Resource<T>(message = message, apiStatus = Status.EMPTY)

    class Failed<T>(message: String? = null, data: T? = null) :
        Resource<T>(apiStatus = Status.FAILED, message = message, data = data)
}