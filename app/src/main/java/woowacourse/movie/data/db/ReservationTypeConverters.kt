package woowacourse.movie.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

class ReservationTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun toSeats(value: List<Seat>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun fromSeats(value: String): List<Seat>? {
        return gson.fromJson(value, Array<Seat>::class.java)?.toList()
    }

    @TypeConverter
    fun toLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.toString()
    }

    @TypeConverter
    fun fromLocalDateTime(dateTimeString: String?): LocalDateTime? {
        return dateTimeString?.let { LocalDateTime.parse(it) }
    }
}
