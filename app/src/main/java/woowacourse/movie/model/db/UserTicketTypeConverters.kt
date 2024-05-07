package woowacourse.movie.model.db

import androidx.room.TypeConverter
import woowacourse.movie.model.movie.Seat
import java.time.LocalDateTime

class UserTicketTypeConverters {
    @TypeConverter
    fun fromLocalDateTime(screeningStartDateTime: LocalDateTime): String {
        return screeningStartDateTime.toString()
    }

    @TypeConverter
    fun toLocalDateTime(screeningStartDateTime: String): LocalDateTime {
        return LocalDateTime.parse(screeningStartDateTime)
    }

    @TypeConverter
    fun fromList(reservationSeats: List<Seat>): String {
        return reservationSeats.joinToString()
    }

    @TypeConverter
    fun toList(reservationSeats: String): List<Seat> {
        return reservationSeats.split(", ").map { Seat(it) }
    }
}
