package woowacourse.movie.data.bookinghistory

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDateTime

class BookingHistoryConverters {
    @TypeConverter
    fun fromSeats(seats: List<Seat>?): String {
        return seats?.joinToString { seat ->
            val row = BASE_ROW_LETTER + seat.seatPosition.y
            val col = seat.seatPosition.x + COLUMN_OFFSET
            "$row$col"
        }.orEmpty()
    }

    @TypeConverter
    fun toSeats(value: String?): List<Seat> {
        if (value.isNullOrBlank()) return emptyList()

        return value.split(SEATS_DELIMITER)
            .map { it.trim() }
            .mapNotNull { token ->
                if (token.length < MIN_SEAT_STRING_LENGTH) return@mapNotNull null
                val row = token[0].uppercaseChar() - BASE_ROW_LETTER
                val col = token.substring(1).toIntOrNull()?.minus(COLUMN_OFFSET) ?: return@mapNotNull null
                Seat.of(col, row)
            }
    }

    @TypeConverter
    fun fromDateTime(dateTime: LocalDateTime?): String? = dateTime?.toString()

    @TypeConverter
    fun toDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it) }

    companion object {
        private const val BASE_ROW_LETTER = 'A'
        private const val COLUMN_OFFSET = 1
        private const val MIN_SEAT_STRING_LENGTH = 2
        private const val SEATS_DELIMITER = ","
    }
}
