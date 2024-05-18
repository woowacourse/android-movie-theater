package woowacourse.movie.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.model.seats.Seats
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun localDateTimeToJson(dateTime: LocalDateTime): String {
        return dateTime.format(
            DateTimeFormatter.ofPattern(DATE_TIME_PATTERN),
        ).toString()
    }

    @TypeConverter
    fun jsonToLocalDateTime(dateTime: String): LocalDateTime {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))
    }

    @TypeConverter
    fun seatsToJson(seats: Seats): String {
        return gson.toJson(seats)
    }

    @TypeConverter
    fun jsonToSeats(seats: String): Seats {
        return gson.fromJson(seats, Seats::class.java)
    }

    companion object {
        private const val DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }
}
