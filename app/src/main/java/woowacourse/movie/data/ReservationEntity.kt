package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.cinema.Seat
import java.time.LocalDateTime

@Entity(tableName = "reservation")
data class ReservationEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime,
    @ColumnInfo(name = "seats") val seats: List<Seat>,
    @ColumnInfo(name = "count") val reservationCount: Int,
    @ColumnInfo(name = "total_price") val totalPrice: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
