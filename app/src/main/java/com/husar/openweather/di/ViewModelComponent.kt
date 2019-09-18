package com.husar.openweather.di

import com.husar.openweather.viewmodel.DetailViewModel
import com.husar.openweather.viewmodel.SearchCityViewModel
import com.husar.openweather.viewmodel.SearchCoordinatorsViewModel
import dagger.Component

@Component(modules = [ApiModule::class, AppModule::class])
interface ViewModelComponent {

    fun inject(viewModel: DetailViewModel)

    fun inject(viewModel: SearchCityViewModel)

    fun inject(viewModel: SearchCoordinatorsViewModel)
}