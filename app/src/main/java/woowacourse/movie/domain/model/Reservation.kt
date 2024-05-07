package woowacourse.movie.domain.model

import java.time.LocalDateTime

data class Reservation(
    val reservationId: Int,
    val theaterId: Int,
    val movie: ScreenView.Movie,
    val ticketCount: Int,
    val seats: List<Seat>,
    val dateTime: LocalDateTime,
) {
    val totalPrice: Int
        get() = seats.sumOf { seat -> seat.seatRank.price }
}
