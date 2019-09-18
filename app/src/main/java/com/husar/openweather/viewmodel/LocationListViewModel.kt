package com.husar.openweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husar.openweather.data.repository.OpenWeatherRepo
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.utility.SingleLiveEvent

class LocationListViewModel(val openWeatherRepo: OpenWeatherRepo): ViewModel() {

    private val _weathersObservable by lazy { MutableLiveData<List<WeatherResponse>>() }
    val weathersObservable: LiveData<List<WeatherResponse>>
        get() = _weathersObservable

    private val _buttonAddObservable: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val buttonAddObservable: LiveData<Boolean>
        get() = _buttonAddObservable

    fun onButtonAddClicked() {
        _buttonAddObservable.value = true
    }

    fun refresh() {
        getWeatherFromDatabase()
    }

    private fun getWeatherFromDatabase() {
        openWeatherRepo.getAllFromDatabase(_weathersObservable)
    }
}