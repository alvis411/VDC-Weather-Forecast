package com.quypham.vdc.repo.weather

import com.quypham.vdc.api.RepoResponse
import com.quypham.vdc.data.WeatherForecastEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherLocalDataSource @Inject constructor(): WeatherDataSource {
    override suspend fun query(city: String, daysOfForecast: Int): RepoResponse<WeatherForecastEntity> {
        throw Exception("not implemented")
    }
}