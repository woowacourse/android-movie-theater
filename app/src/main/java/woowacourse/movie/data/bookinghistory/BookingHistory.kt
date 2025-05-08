package woowacourse.movie.data.bookinghistory

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.seat.Seat
import java.time.LocalDateTime

@Entity(tableName = "booking_histories")
data class BookingHistory(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "movie_title") val title: String?,
    @ColumnInfo(name = "theater_name") val theater: String?,
    @ColumnInfo(name = "screening_date_time") val screeningDateTime: LocalDateTime?,
    @ColumnInfo(name = "head_count") val headCount: Int?,
    @ColumnInfo(name = "total_amount") val totalAmount: Int?,
    @ColumnInfo(name = "selected_seats") val selectedSeats: List<Seat>?,
)
