package woowacourse.movie.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import woowacourse.movie.model.Cinema
import java.time.LocalDate

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun cinemaToJson(cinema: Cinema): String {
        return gson.toJson(cinema)
    }

    @TypeConverter
    fun jsonToCinema(json: String): Cinema {
        val type = object : TypeToken<Cinema>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun localDateToJson(localDate: LocalDate): String {
        return gson.toJson(localDate)
    }

    @TypeConverter
    fun jsonToLocalDate(json: String): LocalDate {
        val type = object : TypeToken<LocalDate>() {}.type
        return gson.fromJson(json, type)
    }
}
