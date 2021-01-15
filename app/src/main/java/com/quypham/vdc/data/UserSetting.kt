package com.quypham.vdc.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.quypham.vdc.database.TempUnitConverter

@Entity(tableName = "userSetting")
@TypeConverters(TempUnitConverter::class)
data class UserSetting constructor(var daysOfForecast: Int, var tempUnit: TempUnit) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

enum class TempUnit {
    KELVIN, METRIC, IMPERIAL
}