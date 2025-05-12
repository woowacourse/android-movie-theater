package woowacourse.movie.db

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

class ReservationInfoConverters {
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String = dateTime.toString()

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String): LocalDateTime = LocalDateTime.parse(dateTimeString)

    @TypeConverter
    fun fromSeats(seats: List<Seat>): String = seats.joinToString(";") { "${it.row},${it.column}" }

    @TypeConverter
    fun toSeats(seats: String): List<Seat> {
        val splitedSeats = seats.split(SEAT_DELIMITER)
        val result =
            splitedSeats.map { seat ->
                val (row, col) = seat.split(ROW_COLUMN_DELIMITER).map { it.toInt() }
                Seat(row, col)
            }

        return result
    }

    companion object {
        private const val SEAT_DELIMITER = ";"
        private const val ROW_COLUMN_DELIMITER = ","
    }
}
