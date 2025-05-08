package woowacourse.movie.data.converter

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TicketConverter {
    @TypeConverter
    fun toLocalDate(value: String): LocalDateTime {
        return LocalDateTime.parse(value, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun fromLocalDate(value: LocalDateTime): String {
        return value.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toReservationCount(value: Int): ReservationCount {
        return ReservationCount(value)
    }

    @TypeConverter
    fun fromReservationCount(value: ReservationCount): Int {
        return value.value
    }

    @TypeConverter
    fun fromSeatList(value: List<Seat>): String {
        return value.joinToString(",") { it.row.toString() + it.column.toString() }
    }

    @TypeConverter
    fun toSeatList(value: String): List<Seat> {
        return value.split(",").map {
            val row = it.substring(0, 1).toInt()
            val column = it.substring(1).toInt()
            Seat(row, column)
        }
    }
}
