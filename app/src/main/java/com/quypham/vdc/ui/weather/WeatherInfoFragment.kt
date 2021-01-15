package com.quypham.vdc.ui.weather

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.quypham.vdc.base.BaseFragment
import com.quypham.vdc.databinding.FragmentWeatherInfoBinding
import com.quypham.vdc.ui.setting.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoFragment : BaseFragment() {
    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): WeatherInfoFragment {
            return WeatherInfoFragment()
        }
    }

    private val mViewModel: WeatherInfoViewModel by viewModels()
    private lateinit var mBinding: FragmentWeatherInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentWeatherInfoBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.etCity.doOnTextChanged { text, _, _, _ ->
            mBinding.rvWeathers.showLoadingView()
            mViewModel.onQueryChange(text.toString())
        }

        mBinding.setting.setOnClickListener {
            val settingFragment = SettingFragment()
            settingFragment.show(childFragmentManager, settingFragment.tag)
        }

        mViewModel.weatherInfos.observe(viewLifecycleOwner, { response ->
            Log.d(TAG, "onWeatherResponse $response")

            if (response.isSuccess) {
                mBinding.rvWeathers.showRecyclerView()
                mBinding.rvWeathers.recyclerView.apply {
                    if (this.adapter == null) {
                        this.adapter = WeatherInfoAdapter()
                    }
                    (this.adapter as WeatherInfoAdapter).apply {
                        Log.d(TAG, "submit new list ${response.weatherInfoList}")
                        submitList(response.weatherInfoList)
                    }
                }
            } else {
                mBinding.rvWeathers.showErrorView(response.errorMessage)
            }
        })
    }

}