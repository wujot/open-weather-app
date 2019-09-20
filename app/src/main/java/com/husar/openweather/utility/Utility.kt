package com.husar.openweather.utility

import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.room.TypeConverter
import java.util.*
import com.google.gson.Gson
import com.husar.openweather.model.WeatherResponse
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter
import com.husar.openweather.data.model.*

fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<T>) {
    removeObserver(observer)
    observe(owner, observer)
}

fun convertResponseToEntity(response: WeatherResponse): WeatherRecord {
    return WeatherRecord(
        response.id,
        response.name,
        Coord(
            response.coord?.lon,
            response.coord?.lat),
        Main(
            response.main?.temp,
            response.main?.pressure,
            response.main?.humidity,
            response.main?.temp_min,
            response.main?.temp_max

        ),
        Weather(
            response.weather?.get(0)?.main,
            response.weather?.get(0)?.description,
            response.weather?.get(0)?.icon
        ),
        Clouds(
          response.clouds?.all
        ),
        Date(System.currentTimeMillis())
    )
}

@BindingAdapter("weatherIcon")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load("http://openweathermap.org/img/w/$imageUrl.png").apply(RequestOptions().circleCrop())
        .into(view)
}


class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return if (value == null) null else Date(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}

class WeatherConverters {

    @TypeConverter
    fun listToJson(value: List<Weather>?): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Weather>? {

        val objects = Gson().fromJson(value, Array<Weather>::class.java) as Array<Weather>
        val list = objects.toList()
        return list
    }
}