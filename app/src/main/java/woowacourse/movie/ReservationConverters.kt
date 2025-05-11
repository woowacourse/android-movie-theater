package woowacourse.movie

import androidx.room.TypeConverter
import woowacourse.movie.domain.reservation.Seat
import java.time.LocalDateTime

class ReservationConverters {
    @TypeConverter
    fun String.convertToSeats(): Set<Seat> =
        split(SEPARATOR_SEATS)
            .map {
                val row: Int = this[0].digitToInt()
                val column: Int = this[1].digitToInt()
                Seat(row, column)
            }.toSet()

    @TypeConverter
    fun Set<Seat>.convertToString(): String =
        joinToString(SEPARATOR_SEATS) { seat ->
            "${seat.row.value}${seat.column.value}"
        }

    @TypeConverter
    fun LocalDateTime.convertToString(): String = this.toString()

    @TypeConverter
    fun String.convertToLocalDateTime(): LocalDateTime = LocalDateTime.parse(this)

    companion object {
        const val SEPARATOR_SEATS = ", "
    }
}
