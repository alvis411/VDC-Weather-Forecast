package com.quypham.vdc.ui.setting

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.quypham.vdc.Constants
import com.quypham.vdc.data.TempUnit
import com.quypham.vdc.data.UserSetting
import com.quypham.vdc.di.AppScope
import com.quypham.vdc.domain.UpdateUserSetting
import com.quypham.vdc.repo.SettingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SettingViewModel @ViewModelInject constructor(
    private val settingRepository: SettingRepository,
    private val updateUserSetting: UpdateUserSetting,
    @AppScope private val appScope: CoroutineScope
) : ViewModel() {
    companion object {
        const val TAG = "SettingViewModel"
    }

    private val _userSetting = MutableLiveData<UserSetting?>()

    init {
         viewModelScope.launch {
            _userSetting.value = settingRepository.getSetting()
        }
    }

    val userSetting = _userSetting

    fun updateSetting(daysForecast: Int? = null, unit: TempUnit? = null) {
        val currentSetting =
            _userSetting.value ?: UserSetting(Constants.DEFAULT_DAYS_FORECAST, TempUnit.KELVIN)
        daysForecast?.let {
            currentSetting.daysOfForecast = daysForecast
        }
        unit?.let {
            currentSetting.tempUnit = unit
        }
        _userSetting.value = currentSetting
    }

    override fun onCleared() {
        appScope.launch {
            updateUserSetting.invoke(_userSetting.value ?: UserSetting(Constants.DEFAULT_DAYS_FORECAST, TempUnit.KELVIN))
        }
        super.onCleared()
    }
}