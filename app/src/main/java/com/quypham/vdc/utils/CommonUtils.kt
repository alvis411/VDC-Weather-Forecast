package com.quypham.vdc.utils


object CommonUtils {
    fun kelvinToCelsius(kelvinDegree: Double): Double {
        return kelvinDegree - 273.15
    }

    fun kelvinToFahrenheit(kelvinDegree: Double): Double {
        return (kelvinDegree - 273.15) * 1.8 + 32.0
    }
}