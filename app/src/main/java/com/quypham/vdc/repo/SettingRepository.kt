package com.quypham.vdc.repo

import androidx.lifecycle.LiveData
import com.quypham.vdc.Constants
import com.quypham.vdc.data.UserSetting
import com.quypham.vdc.repo.setting.SettingDataSource
import javax.inject.Inject
import javax.inject.Named

interface SettingRepository {
    suspend fun getSetting(): UserSetting?
    fun getSettingLive(): LiveData<UserSetting?>
    suspend fun updateSetting(setting: UserSetting)
}
class SettingRepositoryImp @Inject constructor(@Named(Constants.LOCAL) private val localSource: SettingDataSource) : SettingRepository{
    override suspend fun getSetting(): UserSetting? {
        return localSource.getSetting()
    }

    override fun getSettingLive(): LiveData<UserSetting?> {
        return localSource.getSettingLive()
    }

    override suspend fun updateSetting(setting: UserSetting) {
        return localSource.updateSetting(setting)
    }
}