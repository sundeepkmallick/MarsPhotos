package com.marsphoto.network

import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> apiErrorHandler(
    apiCall: suspend () -> T
): ApiResult<T> {
    return try {
        ApiResult.Success(apiCall())
    } catch (e: HttpException) {
        // Backend error (400, 500, etc.)
        val errorBody = e.response()?.errorBody()?.string()
        val apiError = errorBody?.let {
            try {
                val json = JSONObject(it)
                ApiError.BackendError(
                    errorCode = json.optString("errorCode"),
                    errorMessage = json.optString("errorMessage")
                )
            } catch (_: Exception) {
                ApiError.UnknownError
            }
        } ?: ApiError.UnknownError

        ApiResult.Failure(apiError)
    } catch (e: IOException) {
        // Network / timeout errors
        val apiError = when (e) {
            is SocketTimeoutException -> ApiError.TimeoutError
            else -> ApiError.NetworkError(e.message ?: "Network error")
        }
        ApiResult.Failure(apiError)
    } catch (e: Exception) {
        ApiResult.Failure(ApiError.UnknownError)
    }
}
