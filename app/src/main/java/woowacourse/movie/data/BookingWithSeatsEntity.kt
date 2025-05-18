package woowacourse.movie.data

import androidx.room.Embedded
import androidx.room.Relation

data class BookingWithSeatsEntity(
    @Embedded val booking: BookingInfoEntity,
    @Relation(
        parentColumn = "uid",
        entityColumn = "bookingId",
    )
    val selectedSeats: List<BookingSeatEntity>,
)
