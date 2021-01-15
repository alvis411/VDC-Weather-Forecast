package com.quypham.vdc.ui

import android.os.Bundle
import com.quypham.vdc.R
import com.quypham.vdc.base.BaseActivity
import com.quypham.vdc.ui.weather.WeatherInfoFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        var infoFragment: WeatherInfoFragment? = supportFragmentManager.findFragmentByTag(WeatherInfoFragment.TAG) as WeatherInfoFragment?

        if (infoFragment == null) { // first time
            infoFragment = WeatherInfoFragment.newInstance()
        }

        addFragment(infoFragment, WeatherInfoFragment.TAG)
    }

    override fun onBackPressed() {}
}