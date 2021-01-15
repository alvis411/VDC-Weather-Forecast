package com.quypham.vdc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.quypham.vdc.Constants
import com.quypham.vdc.data.UserSetting

@Database(entities = [UserSetting::class], version = Constants.APP_DATABASE_VERSION, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userSettingDao(): UserSettingDao
}