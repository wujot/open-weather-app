package com.husar.openweather.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.husar.openweather.data.repository.OpenWeatherRepo
import com.husar.openweather.model.WeatherResponse

class SearchCoordinatorsViewModel(val openWeatherRepo: OpenWeatherRepo): ViewModel() {

    val latitudeInput by lazy { MutableLiveData<String>() }
    val longitudeInput  by lazy { MutableLiveData<String>() }
    val cityResult by lazy { MutableLiveData<String>() }

    private val _weatherObservable by lazy { MutableLiveData<WeatherResponse>() }
    val weatherObservable: LiveData<WeatherResponse>
        get() = _weatherObservable
    private val _loadErrorObservable by lazy { MutableLiveData<Boolean>() }
    val loadErrorObservable: LiveData<Boolean>
        get() = _loadErrorObservable
    private val _loadingObservable by lazy { MutableLiveData<Boolean>() }
    val loadingObservable: LiveData<Boolean>
        get() = _loadingObservable

    fun onButtonSearchClicked() {
        _loadingObservable.value = true
        getCityByCoordinators()
    }

    private fun getCityByCoordinators() {
        openWeatherRepo.getWeatherFromRemoteByCoordinators(
            latitudeInput.value.toString(),
            longitudeInput.value.toString(),
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