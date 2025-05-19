package woowacourse.movie.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking_seat")
data class BookingSeatEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val bookingId: Int = 0,
    val row: Int,
    val column: Int,
)
