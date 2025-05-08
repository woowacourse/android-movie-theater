package woowacourse.movie.data.db.converter

import androidx.room.TypeConverter
import kotlinx.serialization.builtins.SetSerializer
import kotlinx.serialization.json.Json
import woowacourse.movie.domain.model.seat.Seat

class SeatTypeConverters {
    private val json = Json

    @TypeConverter
    fun fromSeats(seats: Set<Seat>): String {
        return json.encodeToString(SetSerializer(Seat.serializer()), seats)
    }

    @TypeConverter
    fun toSeats(jsonString: String): Set<Seat> {
        return json.decodeFromString(SetSerializer(Seat.serializer()), jsonString)
    }
}
