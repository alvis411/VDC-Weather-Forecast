package com.quypham.vdc.base

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.quypham.vdc.R

abstract class BaseActivity : AppCompatActivity() {
    protected fun addFragment(fragment: Fragment, tag: String) {
        addFragment(fragment, tag, R.id.container)
    }


    private fun addFragment(fragment: Fragment, tag: String?, frameId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(frameId, fragment, tag)
            .addToBackStack(tag)
            .commitAllowingStateLoss()
    }
}