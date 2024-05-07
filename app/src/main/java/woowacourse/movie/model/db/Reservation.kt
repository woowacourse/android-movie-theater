package woowacourse.movie.model.db

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import woowacourse.movie.model.movie.Seat
import java.time.LocalDateTime

data class Reservation(
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "screening_start_date_time") val screeningStartDateTime: LocalDateTime,
    @ColumnInfo(name = "reservation_count") val reservationCount: Int,
    @ColumnInfo(name = "reservation_seats") val reservationSeats: List<Seat>,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "reservation_amount") val reservationAmount: Int,
) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
}
