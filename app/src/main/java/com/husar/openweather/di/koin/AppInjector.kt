package com.husar.openweather.di.koin

import com.husar.openweather.data.remote.OpenWeatherApi
import org.koin.dsl.module
import retrofit2.Retrofit

private val retrofit: Retrofit = createNetworkClient()

private val openWeatherApi: OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)

val networkModule = module {
    single { openWeatherApi }
}