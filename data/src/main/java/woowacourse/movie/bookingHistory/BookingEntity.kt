package woowacourse.movie.bookingHistory

import java.time.LocalDateTime

data class BookingEntity(
    val id: Long,
    val movieId: Long,
    val bookedDateTime: LocalDateTime,
    val paymentType: Int,
    val seats: List<SeatEntity>,
)
