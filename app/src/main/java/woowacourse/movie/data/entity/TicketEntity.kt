package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

@Entity(tableName = "ticket")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "show_time") val showTime: LocalDateTime,
    @ColumnInfo(name = "reservation_count") val reservationCount: ReservationCount,
    @ColumnInfo(name = "cinema_name") val cinemaName: String,
    @ColumnInfo(name = "seats") val seats: List<Seat>,
    @ColumnInfo(name = "total_price") val totalPrice: Int,
)
