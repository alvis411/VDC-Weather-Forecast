package com.quypham.vdc.api

import android.util.Log
import com.google.gson.Gson
import com.quypham.vdc.Constants
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException


suspend fun <T: Any?> handleRequest(requestFunc: suspend () -> Response<T>): RepoResponse<T> {
    return try {
        requestFunc.invoke().createRepoResponse()
    } catch (exception: Exception) {
        Log.d("XXX","handleRequest exception $exception")
        when (exception) {
            is SocketTimeoutException -> {
                Failure(Constants.INTERNAL_HTTP_CLIENT_TIMEOUT, null, exception)
            }

            is UnknownHostException -> {
                Failure(Constants.INTERNAL_HTTP_NO_INTERNET_CONNECTION, null, exception)
            }

            else -> {
                Failure(Constants.INTERNAL_HTTP_UNKNOWN_ERROR, null, exception)
            }
        }
    }
}

fun <T> Response<T>.createRepoResponse(): RepoResponse<T> {
    let { response ->
        Log.d("XXX","createRepoResponse response $response")
        return try {
            createSuccessResponse(response)
        } catch (t: Throwable) {
            createFailure(t)
        }
    }
}

private fun <T> createSuccessResponse(response: Response<T>): RepoResponse<T> {
    return if (response.isSuccessful) {
        val body = response.body()
        var isFromCache = false
        if (response.raw().cacheResponse != null) {
            isFromCache = true
        }

        if (response.raw().networkResponse != null && response.raw().networkResponse!!.code != HttpURLConnection.HTTP_NOT_MODIFIED) {
            isFromCache = false
        }
        Success(response = body, isFromCache = isFromCache)
    } else {
        try {
            Log.d("XXX","createSuccessResponse error response $response")
            val errorContent = response.errorBody()?.string() ?: response.message()
            val serverError = Gson().fromJson(errorContent, ServerError::class.java)
            Failure(response.code(), serverError, null)
        } catch (exception: Exception) {
            Failure(response.code(), null, null)
        }
    }
}

private fun <T> createFailure(throwable: Throwable): Failure<T> {
    Log.d("XXX","createFailure $throwable")
    return when (throwable) {
        is SocketTimeoutException -> {
            Failure(Constants.INTERNAL_HTTP_CLIENT_TIMEOUT, null, throwable)
        }

        is UnknownHostException -> {
            Failure(Constants.INTERNAL_HTTP_NO_INTERNET_CONNECTION, null, throwable)
        }

        else -> {
            Failure(Constants.INTERNAL_HTTP_UNKNOWN_ERROR, null, throwable)
        }
    }
}

