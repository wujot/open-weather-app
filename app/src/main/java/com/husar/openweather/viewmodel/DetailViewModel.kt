package com.husar.openweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husar.openweather.data.repository.OpenWeatherRepo
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.utility.SingleLiveEvent

class DetailViewModel(val openWeatherRepo: OpenWeatherRepo): ViewModel() {

    val currentWeather by lazy { MutableLiveData<WeatherResponse>() }
    private val _weatherObservable by lazy { MutableLiveData<WeatherResponse>() }
    val weatherObservable: LiveData<WeatherResponse>
        get() = _weatherObservable
    private val _loadingObservable by lazy { MutableLiveData<Boolean>() }
    val loadingObservable: LiveData<Boolean>
        get() = _loadingObservable
    private val _loadErrorObservable by lazy { MutableLiveData<Boolean>() }
    val loadErrorObservable: LiveData<Boolean>
        get() = _loadErrorObservable
    private val _deleteWeatherObservable by lazy { SingleLiveEvent<Boolean>() }
    val deleteWeatherObservable: LiveData<Boolean>
    get() = _deleteWeatherObservable

    fun refresh() {
        _loadingObservable.value = true
        getWeatherById()
    }

    private fun getWeatherById() {
        openWeatherRepo.getWeatherFromRemoteById(
            currentWeather,
            _weatherObservable,
            _loadingObservable,
            _loadErrorObservable
        )
    }

    fun delete() {
        currentWeather.value?.let {
            openWeatherRepo.deleteWeatherFromLocal(it, _deleteWeatherObservable)
        }
    }

    override fun onCleared() {
        super.onCleared()

        openWeatherRepo.disposable.clear()
    }
}