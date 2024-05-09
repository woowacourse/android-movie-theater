package woowacourse.movie.db.history

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.SeatSelection

class ReservationHistoryConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromSeats(seatSelection: SeatSelection): String {
        return gson.toJson(seatSelection)
    }

    @TypeConverter
    fun toSeats(seatsString: String): SeatSelection {
        return gson.fromJson(seatsString, SeatSelection::class.java)
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
