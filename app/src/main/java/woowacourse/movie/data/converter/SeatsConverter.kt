package woowacourse.movie.data.converter

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.movie.TicketType
import woowacourse.movie.domain.model.theater.Seat
import woowacourse.movie.domain.model.theater.Seats

class SeatsConverter {
    @TypeConverter
    fun fromSeats(seats: Seats?): String? = seats?.seats?.joinToString { it.toSeatTag() }

    @TypeConverter
    fun toSeats(data: String?): Seats? {
        val seats: List<Seat>? = data?.split(',')?.map { seatFromTag(it) }
        return Seats().apply { seats?.forEach { add(it) } }
    }

    private fun Seat.toSeatTag(): String = "${ASCII_A + row}${col + 1}"

    private fun seatFromTag(tag: String): Seat {
        val row = tag[0] - ASCII_A
        val col = tag[1].digitToInt() - ONE_BASED
        val ticketType = TicketType.ticketTypeByRow(row)
        return Seat(row, col, ticketType)
    }

    companion object {
        private const val ASCII_A = 'A'
        private const val ONE_BASED = 1
    }
}
