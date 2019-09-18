package com.husar.openweather.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.husar.openweather.BuildConfig
import com.husar.openweather.data.local.OpenWeatherDao
import com.husar.openweather.data.remote.OpenWeatherApi
import com.husar.openweather.model.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.*

class OpenWeatherRepo(
    private val context: Context,
    private val openWeatherApi: OpenWeatherApi,
    private val openWeatherDao: OpenWeatherDao
) {

    val disposable = CompositeDisposable()

    fun getWeatherFromRemoteByCity(
        city: String,
        data: MutableLiveData<WeatherResponse>,
        loading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val units = "metric"
        val apiKey = BuildConfig.API_KEY

        disposable.add(
            openWeatherApi.getCurrentWeatherByCity(city, units, apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherResponse>() {
                    override fun onSuccess(currentWeather: WeatherResponse) {
                        storeWeatherLocally(currentWeather)
                        data.value = currentWeather
                        error.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        error.value = true
                    }
                })
        )
    }

    fun getWeatherFromRemoteByCoordinators(
        lat: String,
        long: String,
        data: MutableLiveData<WeatherResponse>,
        loading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val units = "metric"
        val apiKey = BuildConfig.API_KEY

        disposable.add(
            openWeatherApi.getCurrentWeatherData(lat, long, units, apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherResponse>() {
                    override fun onSuccess(currentWeather: WeatherResponse) {
                        storeWeatherLocally(currentWeather)
                        data.value = currentWeather
                        error.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        error.value = true
                    }

                })
        )
    }

    fun getWeatherFromRemoteById(
        storedWeather: MutableLiveData<WeatherResponse>,
        data: MutableLiveData<WeatherResponse>,
        loading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val units = "metric"
        val apiKey = BuildConfig.API_KEY

        disposable.add(
            openWeatherApi.getCurrentWeatherByLocationId(storedWeather.value?.id, units, apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WeatherResponse>() {
                    override fun onSuccess(currentWeather: WeatherResponse) {
                        updateWeatherLocally(currentWeather, storedWeather)
                        data.value = currentWeather
                        error.value = false
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        println("could not connected")
                        loading.value = false
                        error.value = true
                    }

                })
        )
    }

    private fun storeWeatherLocally(weather: WeatherResponse) {
        weather.currentDate = Date(System.currentTimeMillis())
        disposable.add(
            openWeatherDao.insertWeather(weather)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Long>() {
                    override fun onSuccess(result: Long) {
                        weather.uuid = result.toInt()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun updateWeatherLocally(weather: WeatherResponse, storedWeather: MutableLiveData<WeatherResponse>) {
        weather.currentDate = Date(System.currentTimeMillis())
        storedWeather.value?.uuid?.let {
            weather.uuid = it
        }
        disposable.add(
            openWeatherDao.updateWeather(weather)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Int>() {
                    override fun onSuccess(result: Int) {
                        println("Succesfully updated: $result")
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun getAllFromDatabase(data: MutableLiveData<List<WeatherResponse>>) {

        disposable.add(
            openWeatherDao.getAllWeathers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<WeatherResponse>>() {
                    override fun onSuccess(all: List<WeatherResponse>) {
                        data.value = all
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun deleteWeatherFromLocal(data: WeatherResponse, isDeleted: MutableLiveData<Boolean>) {

        disposable.add(
            openWeatherDao.deleteWeather(data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Int>() {
                    override fun onSuccess(deletemItem: Int) {
                        isDeleted.value = true
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun deleteAll() {

        disposable.add(
            openWeatherDao.deleteAll()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Int>() {
                    override fun onSuccess(deletemItems: Int) {
                        println("Deleted items: $deletemItems")
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }
}