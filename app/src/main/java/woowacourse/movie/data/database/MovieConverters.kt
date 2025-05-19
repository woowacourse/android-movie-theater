package woowacourse.movie.data.database

import androidx.room.TypeConverter
import woowacourse.movie.domain.model.HeadCount
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.Seats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MovieConverters {
    private val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    // Movie
    @TypeConverter
    fun fromMovie(movie: Movie): String =
        listOf(
            movie.title,
            movie.startDate.toString(),
            movie.endDate.toString(),
            movie.runningTime.toString(),
        ).joinToString(DELIMITER)

    @TypeConverter
    fun toMovie(data: String): Movie {
        val parts = data.split(DELIMITER)
        return Movie(
            title = parts[0],
            startDate = LocalDate.parse(parts[1]),
            endDate = LocalDate.parse(parts[2]),
            runningTime = parts[3].toInt(),
        )
    }

    // Theater
    @TypeConverter
    fun fromTheater(theater: Theater): String = "${theater.name}$DELIMITER${theater.cancelableTime}"

    @TypeConverter
    fun toTheater(data: String): Theater {
        val parts = data.split(DELIMITER)
        return Theater(
            name = parts[0],
            cancelableTime = parts[1].toInt(),
        )
    }

    // LocalDateTime
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String = dateTime.format(dateTimeFormatter)

    @TypeConverter
    fun toLocalDateTime(data: String): LocalDateTime = LocalDateTime.parse(data, dateTimeFormatter)

    // HeadCount
    @TypeConverter
    fun fromHeadCount(headCount: HeadCount): Int = headCount.value

    @TypeConverter
    fun toHeadCount(value: Int): HeadCount = HeadCount(value)

    // Seats
    @TypeConverter
    fun fromSeats(seats: Seats): String = seats.seats.joinToString(DELIMITER) { "${it.row},${it.col}" }

    @TypeConverter
    fun toSeats(data: String): Seats {
        if (data.isBlank()) return Seats()
        val seatSet =
            data
                .split(DELIMITER)
                .map {
                    val (row, col) = it.split(",").map(String::toInt)
                    Seat.from(row, col)
                }.toSet()
        return Seats(seatSet)
    }

    companion object {
        private const val DELIMITER = "###"
    }
}
