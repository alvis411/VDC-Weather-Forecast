package com.quypham.vdc.repo.setting

import androidx.lifecycle.LiveData
import com.quypham.vdc.data.UserSetting

interface SettingDataSource {
    fun getSettingLive(): LiveData<UserSetting?>
    suspend fun getSetting(): UserSetting?
    suspend fun updateSetting(setting: UserSetting)
}