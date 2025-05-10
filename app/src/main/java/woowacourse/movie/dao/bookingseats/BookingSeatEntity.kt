package woowacourse.movie.dao.bookingseats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.BookingStatus

@Entity(tableName = "booking_seat")
data class BookingSeatEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "row") val row: Int,
    @ColumnInfo(name = "col") val col: Int,
    @ColumnInfo(name = "booking_status_id") val bookingStatusId: Long,
) {
    companion object {
        fun of(bookingStatus: BookingStatus, bookingStatusId: Long): List<BookingSeatEntity> {
            return bookingStatus.seat.seats.map { seat ->
                BookingSeatEntity(
                    row = seat.row.value,
                    col = seat.col.value,
                    bookingStatusId = bookingStatusId
                )
            }
        }
    }
}
