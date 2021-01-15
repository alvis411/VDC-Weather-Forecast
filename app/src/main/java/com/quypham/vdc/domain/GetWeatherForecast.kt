package com.quypham.vdc.domain

import android.util.Log
import com.quypham.vdc.UseCase
import com.quypham.vdc.api.RepoResponse
import com.quypham.vdc.data.QueryForecastParameter
import com.quypham.vdc.data.WeatherForecastEntity
import com.quypham.vdc.di.IoDispatcher
import com.quypham.vdc.repo.WeatherRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

open class GetWeatherForecast @Inject constructor(private val mWeatherRepository: WeatherRepository,
                                                  @IoDispatcher dispatcher: CoroutineDispatcher) :
    UseCase<QueryForecastParameter, RepoResponse<WeatherForecastEntity>>(dispatcher) {
    companion object {
        const val TAG = "GetWeatherForecast"
    }
    override suspend fun execute(parameters: QueryForecastParameter): RepoResponse<WeatherForecastEntity>  {
        Log.d(TAG,"GetWeatherForecast execute with parameter $parameters")
        return mWeatherRepository.query(parameters.city, parameters.setting.daysOfForecast)
    }

}