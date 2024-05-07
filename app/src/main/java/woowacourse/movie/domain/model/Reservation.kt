package woowacourse.movie.domain.model

import java.time.LocalDateTime

data class Reservation(
    val reservationId: Long,
    val theaterId: Int,
    val movieId: Int,
    val title: String,
    val ticketCount: Int,
    val seats: List<Seat>,
    val dateTime: LocalDateTime,
) {
    val totalPrice: Int
        get() = seats.sumOf { seat -> seat.seatRank.price }
}
