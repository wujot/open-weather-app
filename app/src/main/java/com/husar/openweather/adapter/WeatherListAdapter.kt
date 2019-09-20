package com.husar.openweather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.husar.openweather.R
import com.husar.openweather.data.model.WeatherRecord
import com.husar.openweather.databinding.ItemWeatherBinding
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.ui.LocationListFragmentDirections
import com.husar.openweather.ui.WeatherClickListener

class WeatherListAdapter(private val weatherlList: ArrayList<WeatherRecord>):
    RecyclerView.Adapter<WeatherListAdapter.WeatherViewHolder>(), WeatherClickListener {

    fun updateWeatherList(newWeatherList: List<WeatherRecord>) {
        weatherlList.clear()
        weatherlList.addAll(newWeatherList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemWeatherBinding>(inflater, R.layout.item_weather, parent, false)
        return WeatherViewHolder(view)
    }

    override fun getItemCount() = weatherlList.size

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.view.weather = weatherlList[position]
        holder.view.listener = this
    }

    override fun onClick(v: View) {
        for (weather in weatherlList) {
            if (v.tag == weather.name) {
                val action = LocationListFragmentDirections.actionLocationListFragmentToDetailFragment(weather)
                Navigation.findNavController(v).navigate(action)
            }
        }
    }

    class WeatherViewHolder(var view: ItemWeatherBinding): RecyclerView.ViewHolder(view.root)
}