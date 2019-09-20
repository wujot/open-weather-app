package com.husar.openweather.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "WeatherRecord")
data class WeatherRecord(
    var id: Int?,
    var name: String?,
    @Embedded
    var coord: Coord?,
    @Embedded
    var main: Main?,
    @Embedded
    var weather: Weather,
    @Embedded
    var clouds: Clouds?,
    var currentDate: Date?
    ) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        TODO("coord"),
        TODO("main"),
        TODO("weather"),
        TODO("clouds"),
        TODO("currentDate")
    ) {
        uuid = parcel.readInt()
    }

    fun dateToFormattedStringFull(): String {
        val format = SimpleDateFormat("E dd MMM yyyy 'at' hh:mm:ss a", Locale.getDefault())
        return format.format(currentDate)
    }

    fun dateToFormattedStringShort(): String {
        val format = SimpleDateFormat("dd MMMM hh:mm", Locale.getDefault())
        return format.format(currentDate)
    }

    fun temperatureFormattedString(): String {
        return main?.temp?.toInt().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeInt(uuid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherRecord> {
        override fun createFromParcel(parcel: Parcel): WeatherRecord {
            return WeatherRecord(parcel)
        }

        override fun newArray(size: Int): Array<WeatherRecord?> {
            return arrayOfNulls(size)
        }
    }


}

data class Coord(
    var lon: Float?,
    var lat: Float?
)

data class Weather(
    var main: String?,
    var description: String?,
    var icon: String?
)

data class Main(
    var temp: Float?,
    var pressure: Float?,
    var humidity: Float?,
    var temp_min: Float?,
    var temp_max: Float?
)

data class Clouds(
    var all: Float?
)