package woowacourse.movie.data

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationConverters {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.M.d HH:mm")

    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String = dateTime.format(dateTimeFormatter)

    @TypeConverter
    fun toLocalDateTime(value: String): LocalDateTime = LocalDateTime.parse(value, dateTimeFormatter)

    @TypeConverter
    fun fromSeats(seats: Seats): String = seats.value.joinToString(",") { it.label }

    @TypeConverter
    fun toSeats(data: String): Seats {
        val labels = data.split(",").map { Seat(it) }
        return Seats.of(labels)
    }
}
