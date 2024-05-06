package woowacourse.movie.db.history

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats

class ReservationHistoryConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromSeats(seats: Seats): String {
        return gson.toJson(seats)
    }

    @TypeConverter
    fun toSeats(seatsString: String): Seats {
        return gson.fromJson(seatsString, Seats::class.java)
    }

    @TypeConverter
    fun fromScreeningDateTime(dateTime: ScreeningDateTime): String {
        return gson.toJson(dateTime)
    }

    @TypeConverter
    fun toScreeningDateTime(dateTimeString: String): ScreeningDateTime {
        return gson.fromJson(dateTimeString, ScreeningDateTime::class.java)
    }
}
