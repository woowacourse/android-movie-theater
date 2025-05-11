package woowacourse.movie.data.converter

import androidx.room.TypeConverter
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seat
import woowacourse.movie.domain.movieseat.Seats

class SeatsConverter {
    @TypeConverter
    fun fromSeats(seats: Seats): String {
        return seats.all.joinToString("|")
    }

    @TypeConverter
    fun toSeats(seatString: String): Seats {
        if (seatString.isBlank()) return Seats(mutableSetOf())

        val seatList =
            seatString.split("|")
                .map { pair ->
                    val (row, column) = pair.split(",").map { it.toInt() }
                    Seat(Position(row, column))
                }.toMutableSet()

        return Seats(seatList)
    }
}
