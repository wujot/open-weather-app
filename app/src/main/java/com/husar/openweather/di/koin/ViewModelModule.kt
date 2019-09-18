package com.husar.openweather.di.koin

import com.husar.openweather.viewmodel.DetailViewModel
import com.husar.openweather.viewmodel.LocationListViewModel
import com.husar.openweather.viewmodel.SearchCityViewModel
import com.husar.openweather.viewmodel.SearchCoordinatorsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DetailViewModel(get()) }
    viewModel { SearchCityViewModel(get()) }
    viewModel { LocationListViewModel(get()) }
    viewModel { SearchCoordinatorsViewModel(get()) }
}