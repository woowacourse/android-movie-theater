package woowacourse.movie.db

import androidx.room.TypeConverter
import java.time.LocalDateTime

class ReservationInfoConverters {
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? = dateTime?.toString()

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? = dateTimeString?.let { LocalDateTime.parse(it) }
}
