package com.husar.openweather.data.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.husar.openweather.BuildConfig
import com.husar.openweather.data.local.OpenWeatherDao
import com.husar.openweather.data.model.WeatherRecord
import com.husar.openweather.data.remote.OpenWeatherApi
import com.husar.openweather.model.WeatherResponse
import com.husar.openweather.utility.convertResponseToEntity
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
        data: MutableLiveData<WeatherRecord>,
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
                        storeWeatherLocally(convertResponseToEntity(currentWeather))
                        data.value = convertResponseToEntity(currentWeather)
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
        data: MutableLiveData<WeatherRecord>,
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
                        storeWeatherLocally(convertResponseToEntity(currentWeather))
                        data.value = convertResponseToEntity(currentWeather)
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
        storedWeather: MutableLiveData<WeatherRecord>,
        data: MutableLiveData<WeatherRecord>,
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
                        updateWeatherLocally(convertResponseToEntity(currentWeather), storedWeather)
                        data.value = convertResponseToEntity(currentWeather)
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

    fun getWeatherFromRemoteByZipCode(
        zip: String,
        data: MutableLiveData<WeatherRecord>,
        loading: MutableLiveData<Boolean>,
        error: MutableLiveData<Boolean>
    ) {
        val units = "metric"
        val apiKey = BuildConfig.API_KEY

        disposable.add(
            openWeatherApi.getCurrentWeatherByZipCode(zip, units, apiKey)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherResponse>() {
                    override fun onSuccess(currentWeather: WeatherResponse) {
                        storeWeatherLocally(convertResponseToEntity(currentWeather))
                        data.value = convertResponseToEntity(currentWeather)
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

    private fun storeWeatherLocally(weather: WeatherRecord) {
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

    private fun updateWeatherLocally(weather: WeatherRecord, storedWeather: MutableLiveData<WeatherRecord>) {
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

    fun getAllFromDatabase(data: MutableLiveData<List<WeatherRecord>>) {

        disposable.add(
            openWeatherDao.getAllWeathers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<WeatherRecord>>() {
                    override fun onSuccess(all: List<WeatherRecord>) {
                        data.value = all
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }

    fun deleteWeatherFromLocal(data: WeatherRecord, isDeleted: MutableLiveData<Boolean>) {

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
                        println("Deleted items size: $deletemItems")
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }
}