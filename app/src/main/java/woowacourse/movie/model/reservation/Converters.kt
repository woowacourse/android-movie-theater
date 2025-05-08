package woowacourse.movie.model.reservation

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import woowacourse.movie.model.movie.MovieTime
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    fun fromSeats(seats: List<Seat>): String = gson.toJson(seats)

    @TypeConverter
    fun toSeats(json: String): List<Seat> = gson.fromJson(json, object : TypeToken<List<Seat>>() {}.type)

    @TypeConverter
    fun fromTheater(theater: Theater): String = gson.toJson(theater)

    @TypeConverter
    fun toTheater(json: String): Theater = gson.fromJson(json, Theater::class.java)

    @TypeConverter
    fun fromMovieTime(time: MovieTime): String = time.value.format(DateTimeFormatter.ISO_LOCAL_TIME)

    @TypeConverter
    fun toMovieTime(timeString: String): MovieTime = MovieTime(LocalTime.parse(timeString))
}
