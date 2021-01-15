package com.quypham.vdc.repo.setting

import androidx.lifecycle.LiveData
import com.quypham.vdc.data.UserSetting
import com.quypham.vdc.database.AppDatabase
import javax.inject.Inject

class SettingLocalDataSource @Inject constructor(private val appDatabase: AppDatabase): SettingDataSource {
    override fun getSettingLive(): LiveData<UserSetting?> {
        return appDatabase.userSettingDao().getUserSettingLive()
    }

    override suspend fun getSetting(): UserSetting? {
        return appDatabase.userSettingDao().getUserSetting()
    }

    override suspend fun updateSetting(setting: UserSetting) {
        val currentSetting = appDatabase.userSettingDao().getUserSetting() ?: setting
        currentSetting.daysOfForecast = setting.daysOfForecast
        currentSetting.tempUnit = setting.tempUnit
        return appDatabase.userSettingDao().updateUserSetting(currentSetting)
    }
}