package com.quypham.vdc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VDCApp: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}