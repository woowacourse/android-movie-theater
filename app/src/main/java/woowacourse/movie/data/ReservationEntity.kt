package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Seats
import java.time.LocalDateTime

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime,
    @ColumnInfo(name = "ticket_count") val ticketCount: Int,
    @ColumnInfo(name = "seats") val seats: Seats,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "total_price") val totalPrice: Int,
)
