package com.quypham.vdc.repo.weather

import com.quypham.vdc.api.RepoResponse
import com.quypham.vdc.data.WeatherForecastEntity

interface WeatherDataSource {
    suspend fun query(city: String, daysOfForecast: Int): RepoResponse<WeatherForecastEntity>
}