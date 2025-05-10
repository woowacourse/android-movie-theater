package woowacourse.movie.database.typeconverter

import androidx.room.TypeConverter
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.Seats
import woowacourse.movie.model.seat.index.Col
import woowacourse.movie.model.seat.index.Row

class SeatsConverter {
    @TypeConverter
    fun fromSeats(seats: Seats?): String? {
        if (seats == null) return null

        return seats.value.joinToString(SEAT_SEPARATOR) { seat ->
            "${seat.row.index}$SEAT_ROW_COL_SEPARATOR${seat.col.index}"
        }
    }

    @TypeConverter
    fun toSeats(data: String?): Seats? {
        if (data.isNullOrEmpty()) return null

        val seatList =
            data.split(SEAT_SEPARATOR).map { seatString ->
                val (rowIndex, colIndex) =
                    seatString
                        .split(SEAT_ROW_COL_SEPARATOR)
                        .map { it.toInt() }
                Seat(Row(rowIndex), Col(colIndex))
            }

        return Seats.create(seatList)
    }

    companion object {
        private const val SEAT_SEPARATOR = ";"
        private const val SEAT_ROW_COL_SEPARATOR = ","
    }
}
