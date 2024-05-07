package woowacourse.movie.model.movie

import java.time.LocalDateTime

data class UserTicket(
    val movieTitle: String,
    val screeningStartDateTime: LocalDateTime,
    val reservationCount: Int,
    val reservationSeats: List<Seat>,
    val theaterName: String,
    val reservationAmount: Int,
    val id: Long = 0,
)
