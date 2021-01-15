package com.quypham.vdc.ui.weather

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.quypham.vdc.R
import com.quypham.vdc.databinding.ItemWeatherBinding
import com.quypham.vdc.utils.DateFormatUtils

class WeatherInfoAdapter :
    ListAdapter<WeatherInfo, WeatherInfoAdapter.WeatherInfoViewHolder>(WeatherDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherInfoViewHolder {
        return WeatherInfoViewHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), parent.context
        )
    }

    override fun onBindViewHolder(holder: WeatherInfoViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    class WeatherInfoViewHolder(
        private val binding: ItemWeatherBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(weatherInfo: WeatherInfo) {
            binding.tvDate.text = String.format(
                context.getString(R.string.weather_date_format),
                DateFormatUtils.toWeatherDateFormat(weatherInfo.timestamp)
            )
            binding.tvAvgTemp.text = String.format(
                context.getString(R.string.weather_avg_temp_format),
                weatherInfo.averageTemp,
                weatherInfo.tempUnit
            )
            binding.tvPressure.text = String.format(
                context.getString(R.string.weather_pressure_format),
                weatherInfo.pressure
            )
            binding.tvHumidity.text = String.format(
                context.getString(R.string.weather_humidity_format),
                weatherInfo.humidity
            )
            binding.tvDescription.text = String.format(
                context.getString(R.string.weather_description_format),
                weatherInfo.description
            )
        }
    }
}

object WeatherDiff : DiffUtil.ItemCallback<WeatherInfo>() {
    override fun areItemsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem.timestamp == newItem.timestamp
    }

    override fun areContentsTheSame(oldItem: WeatherInfo, newItem: WeatherInfo): Boolean {
        return oldItem == newItem
    }
}