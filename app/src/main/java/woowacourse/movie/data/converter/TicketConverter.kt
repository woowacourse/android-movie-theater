package woowacourse.movie.data.converter

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.ReservationCount
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
}
