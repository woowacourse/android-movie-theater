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
        val seats: List<Seat>? =
            data?.takeIf { it.isNotEmpty() }?.split(',')?.mapNotNull {
                try {
                    seatFromTag(it.trim())
                } catch (e: Exception) {
                    null
                }
            }
        return Seats().apply { seats?.forEach { add(it) } }
    }

    private fun Seat.toSeatTag(): String = "${ASCII_A + row}${col + 1}"

    private fun seatFromTag(tag: String): Seat {
        if (tag.length < 2 || !tag[1].isDigit()) {
            throw IllegalArgumentException("Invalid seat tag format: $tag")
        }
        val row: Int = tag[0] - ASCII_A
        if (row < 0) {
            throw IllegalArgumentException("Invalid row character: ${tag[0]}")
        }

        val col: Int = tag[1].digitToInt() - ONE_BASED
        val ticketType = TicketType.ticketTypeByRow(row)
        return Seat(row, col, ticketType)
    }

    companion object {
        private const val ASCII_A = 'A'
        private const val ONE_BASED = 1
    }
}
