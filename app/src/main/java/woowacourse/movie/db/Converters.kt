package woowacourse.movie.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.model.Seat

class Converters {
    private val gson = Gson()

    @TypeConverter
    fun fromReservation(value: List<Seat>): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun reservationToString(value: String): List<Seat> {
        return gson.fromJson(value, Array<Seat>::class.java).toList()
    }
}
