package com.husar.openweather.di.koin

import com.husar.openweather.viewmodel.*
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DetailViewModel(get()) }
    viewModel { SearchCityViewModel(get()) }
    viewModel { LocationListViewModel(get()) }
    viewModel { SearchCoordinatorsViewModel(get()) }
    viewModel { SearchZipViewModel(get()) }
}