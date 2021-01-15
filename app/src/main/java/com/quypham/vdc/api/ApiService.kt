package com.quypham.vdc.api

import com.quypham.vdc.Constants
import com.quypham.vdc.data.WeatherForecastEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("forecast/daily")
    suspend fun queryForecastDaily(@Query("q") city: String,
                           @Query("cnt") daysOfForecast: Int,
                           @Query("appid") appId: String = Constants.QUERY_APP_ID): Response<WeatherForecastEntity?>
}