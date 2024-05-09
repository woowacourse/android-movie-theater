package woowacourse.movie.repository.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import woowacourse.movie.domain.model.reservation.seat.SelectedSeats

class ReservationTicketConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromSeats(seats: SelectedSeats): String {
        return gson.toJson(seats)
    }

    @TypeConverter
    fun toSeats(seatsString: String): SelectedSeats {
        return gson.fromJson(seatsString, SelectedSeats::class.java)
    }
}
