package com.quypham.vdc.utils

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtils {
    private var WEATHER_INFO_DATE_FORMAT: ThreadLocal<SimpleDateFormat> =
        object : ThreadLocal<SimpleDateFormat>() {
            override fun initialValue(): SimpleDateFormat {
                return SimpleDateFormat("EEE,dd MMM yyyy", Locale.US)
            }
        }


    fun toWeatherDateFormat(timestamp: Long): String {
        val calendarInstance = Calendar.getInstance()
        calendarInstance.timeInMillis = timestamp*1000
        return WEATHER_INFO_DATE_FORMAT.get()?.format(calendarInstance.time) ?: "Weather format not found"
    }
}