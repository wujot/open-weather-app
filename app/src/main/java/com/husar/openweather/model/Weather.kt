package com.husar.openweather.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.husar.openweather.utility.WeatherConverters
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "Weather")
data class WeatherResponse(
    @Embedded
    var coord: Coord?,
    @Embedded
    var weather: ArrayList<Weather>?,
    var base: String?,
    @Embedded
    var main: Main?,
    var visibility: Float?,
    @Embedded
    var wind: Wind?,
    @Embedded
    var clouds: Clouds?,
    var dt: Float?,
    @Embedded
    var sys: Sys?,
    var timezone: Float?,
    var id: Int?,
    var name: String?,
    var cod: Float?
): Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
    var currentDate: Date? = null

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

    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Coord::class.java.classLoader),
        TODO("weather"),
        parcel.readString(),
        parcel.readParcelable(Main::class.java.classLoader),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readParcelable(Wind::class.java.classLoader),
        parcel.readParcelable(Clouds::class.java.classLoader),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readParcelable(Sys::class.java.classLoader),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
        uuid = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(coord, flags)
        parcel.writeString(base)
        parcel.writeParcelable(main, flags)
        parcel.writeValue(visibility)
        parcel.writeParcelable(wind, flags)
        parcel.writeParcelable(clouds, flags)
        parcel.writeValue(dt)
        parcel.writeParcelable(sys, flags)
        parcel.writeValue(timezone)
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeValue(cod)
        parcel.writeInt(uuid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherResponse> {
        override fun createFromParcel(parcel: Parcel): WeatherResponse {
            return WeatherResponse(parcel)
        }

        override fun newArray(size: Int): Array<WeatherResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class Coord(
    var lon: Float?,
    var lat: Float?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(lon)
        parcel.writeValue(lat)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coord> {
        override fun createFromParcel(parcel: Parcel): Coord {
            return Coord(parcel)
        }

        override fun newArray(size: Int): Array<Coord?> {
            return arrayOfNulls(size)
        }
    }
}

data class Weather(
    @ColumnInfo(name = "weather_id")
    var id: Int?,
    var main: String?,
    var description: String?,
    var icon: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(main)
        parcel.writeString(description)
        parcel.writeString(icon)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Weather> {
        override fun createFromParcel(parcel: Parcel): Weather {
            return Weather(parcel)
        }

        override fun newArray(size: Int): Array<Weather?> {
            return arrayOfNulls(size)
        }
    }
}

data class Main(
    var temp: Float?,
    var pressure: Float?,
    var humidity: Float?,
    var temp_min: Float?,
    var temp_max: Float?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(temp)
        parcel.writeValue(pressure)
        parcel.writeValue(humidity)
        parcel.writeValue(temp_min)
        parcel.writeValue(temp_max)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Main> {
        override fun createFromParcel(parcel: Parcel): Main {
            return Main(parcel)
        }

        override fun newArray(size: Int): Array<Main?> {
            return arrayOfNulls(size)
        }
    }
}

data class Wind(
    var speed: Float?,
    var deg: Float?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(speed)
        parcel.writeValue(deg)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wind> {
        override fun createFromParcel(parcel: Parcel): Wind {
            return Wind(parcel)
        }

        override fun newArray(size: Int): Array<Wind?> {
            return arrayOfNulls(size)
        }
    }
}

data class Clouds(
    var all: Float?
): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readValue(Float::class.java.classLoader) as? Float) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(all)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Clouds> {
        override fun createFromParcel(parcel: Parcel): Clouds {
            return Clouds(parcel)
        }

        override fun newArray(size: Int): Array<Clouds?> {
            return arrayOfNulls(size)
        }
    }
}

data class Sys(
    var type: Int?,
    @ColumnInfo(name = "sys_id")
    var id: Int?,
    var message: Float?,
    var country: String?,
    var sunrise: Long?,
    var sunset: Long?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(type)
        parcel.writeValue(id)
        parcel.writeValue(message)
        parcel.writeString(country)
        parcel.writeValue(sunrise)
        parcel.writeValue(sunset)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Sys> {
        override fun createFromParcel(parcel: Parcel): Sys {
            return Sys(parcel)
        }

        override fun newArray(size: Int): Array<Sys?> {
            return arrayOfNulls(size)
        }
    }
}