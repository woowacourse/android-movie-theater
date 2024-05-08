package woowacourse.movie.data.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@ProvidedTypeConverter
class ReservationSeatsConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromSeatMapList(value: List<Map<Int, Int>>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toSeatMapList(value: String?): List<Map<Int, Int>>? {
        val type: Type? = object : TypeToken<List<Map<Int, Int>>>() {}.type
        return gson.fromJson(value, type)
    }
}
