package com.husar.openweather.api

import com.husar.openweather.di.DaggerApiComponent
import com.husar.openweather.model.WeatherResponse
import io.reactivex.Single
import javax.inject.Inject

class WeatherApiService {

    @Inject
    lateinit var api: WeatherApi

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getCurrentWeatherByCity(q: String, units: String, apiKey: String): Single<WeatherResponse> {
        return api.getCurrentWeatherByCity(q, units, apiKey)
    }

    fun getCurrentWeatherData(lat: String, long: String, units: String, apiKey: String): Single<WeatherResponse> {
        return api.getCurrentWeatherData(lat, long, units, apiKey)
    }

    fun getCurrentWeatherByLocationID(id: Int?, units: String, apiKey: String): Single<WeatherResponse> {
        return api.getCurrentWeatherByLocationId(id, units, apiKey)
    }
}