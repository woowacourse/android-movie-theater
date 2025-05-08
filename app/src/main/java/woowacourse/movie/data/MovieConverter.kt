package woowacourse.movie.data

import androidx.room.TypeConverter
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Seat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class MovieConverter {
    private val dateHyphenFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-M-d")
    private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    @TypeConverter
    fun fromDate(date: LocalDate): String = date.format(dateHyphenFormatter)

    @TypeConverter
    fun toDate(date: String): LocalDate = LocalDate.parse(date, dateHyphenFormatter)

    @TypeConverter
    fun fromTime(time: LocalTime): String = time.format(timeFormatter)

    @TypeConverter
    fun toTime(time: String): LocalTime = LocalTime.parse(time, timeFormatter)

    @TypeConverter
    fun fromSeats(seats: Set<Seat>): String {
        return seats.map { it.toUiModel() }.joinToString(", ") { point ->
            "${'A' + point.row}${point.col + 1}"
        }
    }

    @TypeConverter
    fun toSeats(seats: String): Set<Seat> {
        return seats.split(", ")
            .map { seat ->
                val row = seat[0] - 'A'
                val col = seat.substring(1).toInt() - 1
                Seat(row, col)
            }.toSet()
    }
}
