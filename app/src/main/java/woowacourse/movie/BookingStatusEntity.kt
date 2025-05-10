package woowacourse.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.BookingStatus
import java.time.LocalDateTime

@Entity(tableName = "booking_status")
data class BookingStatusEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "reservation_date_time") val reservationDateTime: String,
    @ColumnInfo(name = "theater") val theater: String,
) {
    companion object {
        fun of(bookingStatus: BookingStatus): BookingStatusEntity {
            return BookingStatusEntity(
                movieTitle = bookingStatus.movie.title,
                reservationDateTime = bookingStatus.bookedTime.toString(),
                theater = bookingStatus.theater.name,
            )
        }
    }
}
