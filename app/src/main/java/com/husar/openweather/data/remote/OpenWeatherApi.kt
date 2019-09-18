package com.husar.openweather.data.remote

import com.husar.openweather.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("data/2.5/weather?")
    fun getCurrentWeatherByCity(@Query("q") q: String, @Query("units") units: String, @Query("APPID") app_id: String): Single<WeatherResponse>

    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("APPID") app_id: String): Single<WeatherResponse>

    @GET("data/2.5/weather?")
    fun getCurrentWeatherByLocationId(@Query("id") id: Int?, @Query("units") units: String, @Query("APPID") app_id: String): Single<WeatherResponse>
}