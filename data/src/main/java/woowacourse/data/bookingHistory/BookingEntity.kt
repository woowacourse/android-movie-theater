package woowacourse.data.bookingHistory

import java.time.LocalDateTime

data class BookingEntity(
    val id: Long,
    val movieId: Long,
    val movieTitle: String,
    val bookedDateTime: LocalDateTime,
    val paymentType: Int,
    val seats: List<SeatEntity>,
)
