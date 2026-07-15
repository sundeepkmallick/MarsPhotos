package com.marsphoto.network

sealed class ApiError {
    data class BackendError(val errorCode: String, val errorMessage: String) : ApiError()
    data class NetworkError(val message: String) : ApiError()
    object UnknownError : ApiError()
    object TimeoutError : ApiError()
}