package woowacourse.movie.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.domain.model.Reservation
import java.time.LocalDate
import java.time.LocalTime

class ReservationHistoryConverter {
    private val gson: Gson = Gson()

    @TypeConverter
    fun reservationToJson(value: Reservation): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun jsonToReservation(value: String): Reservation {
        return gson.fromJson(value, Reservation::class.java)
    }

    @TypeConverter
    fun toLocalDate(localDate: LocalDate?): String? {
        return localDate?.toString()
    }

    @TypeConverter
    fun fromLocalDate(localDate: String?): LocalDate? {
        return localDate?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun toLocalTime(localTime: LocalTime?): String? {
        return localTime?.toString()
    }

    @TypeConverter
    fun fromLocalTime(value: String?): LocalTime? {
        return value?.let { LocalTime.parse(it) }
    }
}
