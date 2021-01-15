package com.quypham.vdc.domain

import android.util.Log
import com.quypham.vdc.UseCase
import com.quypham.vdc.data.UserSetting
import com.quypham.vdc.di.IoDispatcher
import com.quypham.vdc.repo.SettingRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class UpdateUserSetting  @Inject constructor(private val settingRepository: SettingRepository,
                                             @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<UserSetting, Any>(dispatcher) {
    companion object {
        const val TAG = "UpdateUserSetting"
    }
    override suspend fun execute(parameters: UserSetting): Any {
        Log.d(TAG,"UpdateUserSetting execute")
        settingRepository.updateSetting(parameters)
        return Any()
    }

}