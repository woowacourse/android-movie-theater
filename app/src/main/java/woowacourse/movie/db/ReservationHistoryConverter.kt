package woowacourse.movie.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import java.time.LocalDate
import java.time.LocalTime

class ReservationHistoryConverter {
    @TypeConverter
    fun localDateToJson(value: LocalDate): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToLocalDate(value: String): LocalDate {
        return Gson().fromJson(value, LocalDate::class.java)
    }

    @TypeConverter
    fun localTimeToJson(value: LocalTime): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToLocalTime(value: String): LocalTime {
        return Gson().fromJson(value, LocalTime::class.java)
    }
}
