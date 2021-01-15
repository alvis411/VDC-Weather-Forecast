package com.quypham.vdc.repo.weather

import android.util.Log
import com.quypham.vdc.Constants
import com.quypham.vdc.api.*
import com.quypham.vdc.data.WeatherForecastEntity
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val mApiService: ApiService): WeatherDataSource {
    companion object {
        const val TAG = "WeatherRemoteDataSource"
    }

    override suspend fun query(city: String, daysOfForecast: Int): RepoResponse<WeatherForecastEntity> {
        val response = handleRequest {
            mApiService.queryForecastDaily(city, daysOfForecast)
        }
        Log.d(TAG,"query of $city in $daysOfForecast days response $response")
        return when (response) {
            is Success -> {
                val weatherRemotes = response.response

                if (weatherRemotes != null) {
                    Success(weatherRemotes)
                } else {
                    Failure(Constants.INTERNAL_HTTP_NO_RESULT, null, null, null,null)
                }
            }
            is Failure -> {
                Failure(response.code, response.serverError, null, null,null)
            }
        }

    }
}