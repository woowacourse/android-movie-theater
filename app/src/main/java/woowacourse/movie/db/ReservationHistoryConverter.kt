package woowacourse.movie.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Theater

class ReservationHistoryConverter {
    @TypeConverter
    fun reservationToJson(value: Reservation): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToReservation(value: String): Reservation {
        return Gson().fromJson(value, Reservation::class.java)
    }

    @TypeConverter
    fun theaterToJson(value: Theater): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToTheater(value: String): Theater {
        return Gson().fromJson(value, Theater::class.java)
    }
}
