package woowacourse.movie

import androidx.room.TypeConverter
import woowacourse.movie.domain.reservation.Seat

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
            "${seat.row}${seat.column}"
        }

    companion object {
        const val SEPARATOR_SEATS = ", "
    }
}
