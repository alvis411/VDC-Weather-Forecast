package com.quypham.vdc.database

import androidx.room.TypeConverter
import com.quypham.vdc.data.TempUnit


class TempUnitConverter {
    @TypeConverter
    fun fromTempUnitToString(tempUnit: TempUnit): String  {
        return tempUnit.name
    }

    @TypeConverter
    fun fromStringToTempUnit(value: String): TempUnit {
        return TempUnit.valueOf(value)
    }
}