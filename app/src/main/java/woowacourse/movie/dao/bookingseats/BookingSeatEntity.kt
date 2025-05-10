package woowacourse.movie.dao.bookingseats

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.BookingStatus

@Entity(tableName = "booking_seat")
data class BookingSeatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "row") val row: Int,
    @ColumnInfo(name = "col") val col: Int,
) {
    companion object {
        fun of(bookingStatus: BookingStatus) {
            bookingStatus.seat.seats.forEach { seat ->
                BookingSeatEntity(
                    row = seat.row.value,
                    col = seat.col.value,
                )
            }
        }
    }
}
