package com.quypham.vdc.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.quypham.vdc.data.UserSetting

@Dao
interface UserSettingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUserSetting(setting: UserSetting)

    @Query("SELECT * FROM userSetting")
    fun getUserSettingLive(): LiveData<UserSetting?>

    @Query("SELECT * FROM userSetting")
    suspend fun getUserSetting(): UserSetting?
}