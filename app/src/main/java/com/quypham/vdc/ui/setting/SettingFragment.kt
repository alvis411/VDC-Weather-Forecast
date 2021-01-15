package com.quypham.vdc.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.quypham.vdc.Constants
import com.quypham.vdc.R
import com.quypham.vdc.data.TempUnit
import com.quypham.vdc.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BottomSheetDialogFragment()  {
    companion object {
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }

    private val mViewModel: SettingViewModel by viewModels()
    private lateinit var mBinding: FragmentSettingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentSettingBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding.tvImperial.setOnClickListener {
            mViewModel.updateSetting(unit = TempUnit.IMPERIAL)
        }

        mBinding.tvMetric.setOnClickListener {
            mViewModel.updateSetting(unit = TempUnit.METRIC)
        }
        
        mBinding.tvKelvin.setOnClickListener {
            mViewModel.updateSetting(unit = TempUnit.KELVIN)
        }

        mBinding.npDaysForecast.apply {
            this.minValue = Constants.MINIMUM_DAYS_FORECAST
            this.maxValue = Constants.MAXIMUM_DAYS_FORECAST
            this.setOnValueChangedListener { _, _, newVal ->
                mViewModel.updateSetting(daysForecast = newVal)
            }
        }

        mViewModel.userSetting.observe(viewLifecycleOwner, {
            mBinding.npDaysForecast.value = it?.daysOfForecast ?: Constants.DEFAULT_DAYS_FORECAST
            switchMetric(it?.tempUnit ?: TempUnit.KELVIN)
        })

    }

    private fun switchMetric(unit: TempUnit) {
        mBinding.apply {
            this.tvKelvin.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_white_background)
            this.tvImperial.background = ContextCompat.getDrawable(requireContext(), R.drawable.end_rounded_white_background)
            this.tvMetric.background = ContextCompat.getDrawable(requireContext(), R.drawable.start_rounded_white_background)
            this.tvMetric.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            this.tvImperial.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))
            this.tvKelvin.setTextColor(ContextCompat.getColor(requireContext(),R.color.black))

            when (unit) {
                TempUnit.METRIC -> {
                    this.tvMetric.background = ContextCompat.getDrawable(requireContext(), R.drawable.start_rounded_black_background)
                    this.tvMetric.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                }
                TempUnit.IMPERIAL -> {
                    this.tvImperial.background = ContextCompat.getDrawable(requireContext(), R.drawable.end_rounded_black_background)
                    this.tvImperial.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                }
                TempUnit.KELVIN -> {
                    this.tvKelvin.background = ContextCompat.getDrawable(requireContext(), R.drawable.outline_black_background)
                    this.tvKelvin.setTextColor(ContextCompat.getColor(requireContext(),R.color.white))
                }
            }
        }
    }
}