package woowacourse.movie.data.db

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationConverters {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN_DATE_TIME)

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String = dateTime.format(dateTimeFormatter)

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime = LocalDateTime.parse(value, dateTimeFormatter)

    @TypeConverter
    fun fromSeats(seats: Seats): String = seats.value.joinToString { it.label }

    @TypeConverter
    fun toSeats(data: String): Seats {
        val labels = data.split(DELIMITER_SEATS).map { Seat(it) }
        return Seats.of(labels)
    }

    companion object {
        private const val PATTERN_DATE_TIME = "yyyy.M.d HH:mm"
        private const val DELIMITER_SEATS = ", "
    }
}
