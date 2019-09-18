package com.husar.openweather.di

import com.husar.openweather.api.WeatherApiService
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {

    fun inject(service: WeatherApiService)
}