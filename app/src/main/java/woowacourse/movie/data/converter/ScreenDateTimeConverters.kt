package woowacourse.movie.data.converter

import androidx.room.TypeConverter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalTime

class ScreenDateTimeConverters {
    private val gson =
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateAdapter())
            .registerTypeAdapter(LocalTime::class.java, LocalTimeAdapter())
            .create()

    @TypeConverter
    fun fromScreenDateTimes(value: List<Map<LocalDate, List<LocalTime>>>?): String? {
        val type: Type = object : TypeToken<List<Map<LocalDate, List<LocalTime>>>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toScreenDateTimes(value: String?): List<Map<LocalDate, List<LocalTime>>>? {
        if (value == null) {
            return null
        }
        val type: Type = object : TypeToken<List<Map<LocalDate, List<LocalTime>>>>() {}.type
        return gson.fromJson(value, type)
    }
}
