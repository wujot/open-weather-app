package com.husar.openweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husar.openweather.data.model.WeatherRecord
import com.husar.openweather.data.repository.OpenWeatherRepo

class SearchCityViewModel(val openWeatherRepo: OpenWeatherRepo): ViewModel() {

    val cityInput by lazy { MutableLiveData<String>() }
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
        _loadingObservable.value = true
        getWeatherByCity()
    }

    private fun getWeatherByCity() {
        openWeatherRepo.getWeatherFromRemoteByCity(
            cityInput.value.toString(),
            _weatherObservable,
            _loadingObservable,
            _loadErrorObservable
        )
    }

    override fun onCleared() {
        super.onCleared()

        openWeatherRepo.disposable.clear()
    }
}