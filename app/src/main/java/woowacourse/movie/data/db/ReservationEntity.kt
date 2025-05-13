package woowacourse.movie.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "reservation")
data class ReservationEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime,
    @ColumnInfo(name = "seats") val convertedSeats: String,
    @ColumnInfo(name = "count") val reservationCount: Int,
    @ColumnInfo(name = "total_price") val totalPrice: Int,
)
