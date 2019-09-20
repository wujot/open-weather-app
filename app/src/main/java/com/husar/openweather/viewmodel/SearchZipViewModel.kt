package com.husar.openweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husar.openweather.data.model.WeatherRecord
import com.husar.openweather.data.repository.OpenWeatherRepo

class SearchZipViewModel(val openWeatherRepo: OpenWeatherRepo) : ViewModel() {

    val zipInput by lazy { MutableLiveData<String>() }
    private val _weatherObservable by lazy { MutableLiveData<WeatherRecord>() }
    val weatherObservable: LiveData<WeatherRecord>
        get() = _weatherObservable
    private val _loadErrorObservable by lazy { MutableLiveData<Boolean>() }
    val loadErrorObservable: LiveData<Boolean>
        get() = _loadErrorObservable
    private val _loadingObservable by lazy { MutableLiveData<Boolean>() }
    val loadingObservable: LiveData<Boolean>
        get() = _loadingObservable

    fun onButtonSearchClicked() {
        getWeatherByZipCode()
    }

    private fun getWeatherByZipCode() {
        openWeatherRepo.getWeatherFromRemoteByZipCode(
            zipInput.value.toString(),
            _weatherObservable,
            _loadingObservable,
            _loadErrorObservable
        )
    }
}