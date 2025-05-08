package woowacourse.movie.data

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.cinema.Seat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ReservationConverters {
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? = dateTime?.format(FORMATTER)

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? = dateTimeString?.let { LocalDateTime.parse(it, FORMATTER) }

    @TypeConverter
    fun fromSeatList(seats: List<Seat>?): String = seats?.joinToString(SEAT_DELIMITER) { "${it.row}$COORDINATE_DELIMITER${it.col}" } ?: ""

    @TypeConverter
    fun toSeatList(data: String?): List<Seat> {
        if (data.isNullOrBlank()) return emptyList()
        return data.split(SEAT_DELIMITER).mapNotNull {
            val parts = it.split(COORDINATE_DELIMITER)
            if (parts.size == EXPECTED_COORDINATE_PARTS) {
                val row = parts[0].toIntOrNull()
                val col = parts[1].toIntOrNull()
                if (row != null && col != null) Seat(row, col) else null
            } else {
                null
            }
        }
    }

    companion object {
        private val FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        private const val SEAT_DELIMITER = ";"
        private const val COORDINATE_DELIMITER = ","
        private const val EXPECTED_COORDINATE_PARTS = 2
    }
}
