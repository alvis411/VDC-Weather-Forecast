package com.quypham.vdc.api

import android.util.Log
import com.google.gson.Gson
import com.quypham.vdc.Constants
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class RepoResponse<T> {
    companion object {
        fun <T> create(error: Throwable): Failure<T> {
            Log.d("XXX","handleRequest error $error")
            return when (error) {
                is SocketTimeoutException -> {
                    Failure(Constants.INTERNAL_HTTP_CLIENT_TIMEOUT, null, error)
                }

                is UnknownHostException -> {
                    Failure(Constants.INTERNAL_HTTP_NO_INTERNET_CONNECTION, null, error)
                }

                else -> {
                    Failure(Constants.INTERNAL_HTTP_UNKNOWN_ERROR, null, error)
                }
            }
        }

        fun <T> create(response: Response<T>): RepoResponse<T> {
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
                Log.d("XXX","create exception $response")
                try {
                    val errorContent = response.errorBody()?.string() ?: response.message()
                    val serverError = Gson().fromJson(errorContent, ServerError::class.java)
                    Failure(response.code(), serverError, null)
                } catch (exception: Exception) {
                    Failure(response.code(), null, null)
                }
            }
        }
    }
}

data class Success<T>(val response: T?, val isFromCache: Boolean = false) : RepoResponse<T>()

data class Failure<T>(val code: Int, val serverError: ServerError?, val throwable: Throwable? = null, val errorItems: String? = null, val data : T? = null) : RepoResponse<T>()

