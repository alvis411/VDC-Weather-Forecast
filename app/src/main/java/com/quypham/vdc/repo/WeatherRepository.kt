package com.quypham.vdc.repo

import android.util.Log
import com.quypham.vdc.Constants
import com.quypham.vdc.api.RepoResponse
import com.quypham.vdc.data.WeatherForecastEntity
import com.quypham.vdc.repo.weather.WeatherDataSource
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

interface WeatherRepository {
    suspend fun query(city: String, daysOfForecast: Int): RepoResponse<WeatherForecastEntity>
}

@Singleton
class WeatherRepositoryImp @Inject constructor(@Named(Constants.REMOTE) private val mWeatherRemoteDataSource: WeatherDataSource): WeatherRepository {
    companion object {
        const val TAG = "WeatherRepository"
    }
    override suspend fun query(city: String, daysOfForecast: Int): RepoResponse<WeatherForecastEntity> {
        Log.d(TAG,"query with city $city days $daysOfForecast")
        return mWeatherRemoteDataSource.query(city, daysOfForecast)
    }
}