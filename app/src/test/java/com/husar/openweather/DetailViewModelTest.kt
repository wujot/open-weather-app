package com.husar.openweather

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.husar.openweather.api.WeatherApiService
import com.husar.openweather.di.AppModule
import com.husar.openweather.di.DaggerViewModelComponent
import com.husar.openweather.model.Coord
import com.husar.openweather.model.Main
import com.husar.openweather.model.Weather
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.viewmodel.DetailViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor

class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var weatherService: WeatherApiService

    lateinit var testWeather: WeatherResponse

    val application = Mockito.mock(Application::class.java)

    var detailViewModel = DetailViewModel(application, true)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        DaggerViewModelComponent.builder()
            .appModule(AppModule(application))
            .apiModule(ApiModuleTest(weatherService))
            .build()
            .inject(detailViewModel)

        testWeather = WeatherResponse(
            Coord(1F, 2F),
            null,
            null,
            Main(43F, 1023F, 100F, 23F, 46F),
            null,
            null,
            null,
            null,
            null,
            null,
            123,
            "London",
            null)
    }

    @Test
    fun `test on loading `() {
        val observer = Mockito.mock(Observer::class.java) as Observer<in Boolean>
        detailViewModel.loadingObservable.observeForever(observer)

        detailViewModel.refresh()

        Mockito.verify(observer).onChanged(true)
    }

    @Test
    fun `test on get weather from remote success`() {
        val  currentWeather = testWeather
        val testSingle = Single.just(currentWeather)
        Mockito.`when`(weatherService.getCurrentWeatherByLocationID(123, "metric", "xyz")).thenReturn(testSingle)

        detailViewModel.refresh()

        Assert.assertEquals("london", detailViewModel.currentWeather.value?.name)
        Assert.assertEquals(false, detailViewModel.loadErrorObservable)
        Assert.assertEquals(false, detailViewModel.loadingObservable)
    }


    @Before
    fun setupRxSchedulers() {
        val immediate = object: Scheduler() {
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() }, true)
            }
        }

        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
    }
}