package com.quypham.vdc.ui.weather

import android.content.Context
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.quypham.vdc.Constants
import com.quypham.vdc.Constants.DEFAULT_DAYS_FORECAST
import com.quypham.vdc.Constants.MINIMUM_CITY_CHARACTERS_ALLOW
import com.quypham.vdc.R
import com.quypham.vdc.api.Failure
import com.quypham.vdc.api.Success
import com.quypham.vdc.data
import com.quypham.vdc.data.QueryForecastParameter
import com.quypham.vdc.data.TempUnit
import com.quypham.vdc.data.UserSetting
import com.quypham.vdc.domain.GetWeatherForecast
import com.quypham.vdc.repo.SettingRepository
import com.quypham.vdc.utils.CommonUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class WeatherInfoViewModel @ViewModelInject constructor(
    private val getWeatherForecast: GetWeatherForecast,
    settingRepository: SettingRepository,
    @ApplicationContext private val context: Context
) :
    ViewModel() {
    companion object {
        const val TAG = "WeatherInfoViewModel"
    }

    private val _queryParameterLive = MutableLiveData<QueryForecastParameter>()
    private val _settingLive = settingRepository.getSettingLive()
    private var _currentSetting = UserSetting(DEFAULT_DAYS_FORECAST, TempUnit.KELVIN)

    //display symbol
    private val celsiusSymbol by lazy {
        context.getString(R.string.unit_metric)
    }
    private val kelvinSymbol by lazy {
        context.getString(R.string.unit_kelvin)
    }
    private val fahrenheitSymbol by lazy {
        context.getString(R.string.unit_imperial)
    }

    private val _weatherQueryResponse =
        _queryParameterLive.switchMap<QueryForecastParameter, WeatherUiResponse> {
            liveData {
                emit(
                    when {
                        it.city.isEmpty() -> {
                            WeatherUiResponse(
                                isSuccess = false,
                                errorMessage = context.getString(R.string.empty_city_query)
                            )
                        }
                        it.city.length < MINIMUM_CITY_CHARACTERS_ALLOW -> {
                            WeatherUiResponse(
                                isSuccess = false,
                                errorMessage = context.getString(R.string.invalid_city_name)
                            )
                        }
                        else -> {
                            val response = getWeatherForecast(it).data
                            Log.d(TAG, "getDailyForecast result $response")
                            when (response) {
                                is Success -> {
                                    //convert weather model to ui expected model
                                    if (response.response != null) {
                                        if (response.response.list.isNotEmpty()) {
                                            val correctUnitData =
                                                response.response.list.map { weatherItem ->
                                                    val tempSymbol: String
                                                    val weatherDesc =
                                                        if (weatherItem.weather.isNotEmpty()) {
                                                            weatherItem.weather[0].description
                                                        } else {
                                                            context.getString(R.string.description_not_found)
                                                        }

                                                    val averageTemp =
                                                        (weatherItem.temp.max + weatherItem.temp.min) / 2
                                                    val correctAverageTempWithUnit =
                                                        when (it.setting.tempUnit) {
                                                            TempUnit.METRIC -> {
                                                                tempSymbol = celsiusSymbol
                                                                CommonUtils.kelvinToCelsius(
                                                                    averageTemp
                                                                )
                                                                    .toInt()
                                                            }
                                                            TempUnit.IMPERIAL -> {
                                                                tempSymbol = fahrenheitSymbol
                                                                CommonUtils.kelvinToFahrenheit(
                                                                    averageTemp
                                                                ).toInt()
                                                            }
                                                            else -> {
                                                                tempSymbol = kelvinSymbol
                                                                averageTemp.toInt()
                                                            }
                                                        }

                                                    WeatherInfo(
                                                        weatherItem.dt,
                                                        correctAverageTempWithUnit,
                                                        weatherItem.pressure,
                                                        weatherItem.humidity,
                                                        weatherDesc,
                                                        tempSymbol
                                                    )
                                                }

                                            WeatherUiResponse(
                                                true,
                                                weatherInfoList = correctUnitData
                                            )
                                        } else {
                                            WeatherUiResponse(
                                                isSuccess = false,
                                                errorMessage = context.getString(R.string.empty_result_error)
                                            )
                                        }
                                    } else {
                                        WeatherUiResponse(
                                            isSuccess = false,
                                            errorMessage = context.getString(R.string.general_error)
                                        )
                                    }
                                }

                                is Failure -> {
                                    val httpCodeErrorMessage = when (response.code) {
                                        Constants.UNSATISFIABLE_REQUEST -> context.getString(R.string.no_internet_connection)
                                        Constants.INTERNAL_HTTP_CLIENT_TIMEOUT -> context.getString(
                                            R.string.network_timeout
                                        )
                                        else -> context.getString(R.string.general_error)
                                    }
                                    WeatherUiResponse(
                                        isSuccess = false,
                                        errorMessage = response.serverError?.message
                                            ?: httpCodeErrorMessage
                                    )
                                }
                                else -> {
                                    WeatherUiResponse(
                                        isSuccess = false,
                                        errorMessage = context.getString(R.string.general_error)
                                    )
                                }
                            }
                        }
                    }
                )
            }

        }

    init {
        viewModelScope.launch {
            _settingLive.asFlow().collect { updatedSetting ->
                if (updatedSetting == null) return@collect

                //process data when setting is changed
                _queryParameterLive.value?.let {
                    //day of forecast change, re query this city data
                    it.setting.daysOfForecast = updatedSetting.daysOfForecast
                    it.setting.tempUnit = updatedSetting.tempUnit
                    _queryParameterLive.postValue(it)
                }

                _currentSetting = updatedSetting
            }
        }
    }

    val weatherInfos: LiveData<WeatherUiResponse> = _weatherQueryResponse

    fun onQueryChange(city: String) {
        Log.d(TAG, "onQueryChange city $city")
        _queryParameterLive.postValue(QueryForecastParameter(city, _currentSetting))
    }
}

data class WeatherInfo constructor(
    var timestamp: Long, var averageTemp: Int, var pressure: Int,
    var humidity: Int, var description: String, var tempUnit: String
)

data class WeatherUiResponse(
    val isSuccess: Boolean, val errorMessage: String = "",
    val weatherInfoList: List<WeatherInfo>? = null
)
