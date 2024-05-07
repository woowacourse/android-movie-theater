package woowacourse.movie.data

import androidx.room.TypeConverter
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.formatSeats
import woowacourse.movie.util.unFormatSeats
import java.time.LocalDate
import java.time.LocalTime

class TicketConverters {
    @TypeConverter
    fun toString(localDate: LocalDate): String {
        return localDate.toString()
    }

    @TypeConverter
    fun toLocalDate(string: String): LocalDate {
        return LocalDate.parse(string)
    }

    @TypeConverter
    fun toString(localTime: LocalTime): String {
        return localTime.toString()
    }

    @TypeConverter
    fun toLocalTime(string: String): LocalTime {
        return LocalTime.parse(string)
    }

    @TypeConverter
    fun toString(selectedSeats: MovieSelectedSeats): String {
        return selectedSeats.formatSeats()
    }

    @TypeConverter
    fun toSelectedSeats(string: String): MovieSelectedSeats {
        return string.unFormatSeats()
    }
}
